package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.TencentCosConfig;
import cn.hdustea.aha_server.vo.CosPolicyVo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * 腾讯云COS服务类
 *
 * @author STEA_YY
 **/
@Service
public class CosService {
    @Resource
    private COSClient cosClient;
    @Resource
    private TencentCosConfig tencentCosConfig;
    @Resource
    private RedisService redisService;

    public CosPolicyVo signUploadAuthorization(String filename, boolean isResource) {
        String bucketName;
        if (isResource) {
            bucketName = tencentCosConfig.getResourceBucketName();
        } else {
            bucketName = tencentCosConfig.getPublicBucketName();
        }
        Date expiration = new Date(System.currentTimeMillis() + tencentCosConfig.getExpireTime() * 1000L);
        COSCredentials cosCredentials = new BasicCOSCredentials(tencentCosConfig.getSecretId(), tencentCosConfig.getSecretKey());
        COSSigner cosSigner = new COSSigner();
        String authorization = cosSigner.buildAuthorizationStr(HttpMethodName.PUT, filename, cosCredentials, expiration);
        CosPolicyVo cosPolicyVo = new CosPolicyVo();
        cosPolicyVo.setBucketName(bucketName);
        cosPolicyVo.setRegion(tencentCosConfig.getRegion());
        cosPolicyVo.setAuthorization(authorization);
        cosPolicyVo.setFilename(filename);
        cosPolicyVo.setExpire(expiration.getTime());
        return cosPolicyVo;
    }

    public CosPolicyVo signDownloadAuthorization(String filename) {
        Date expiration = new Date(System.currentTimeMillis() + tencentCosConfig.getExpireTime() * 1000L);
        COSCredentials cosCredentials = new BasicCOSCredentials(tencentCosConfig.getSecretId(), tencentCosConfig.getSecretKey());
        COSSigner cosSigner = new COSSigner();
        String authorization = cosSigner.buildAuthorizationStr(HttpMethodName.GET, filename, cosCredentials, expiration);
        CosPolicyVo cosPolicyVo = new CosPolicyVo();
        cosPolicyVo.setBucketName(tencentCosConfig.getResourceBucketName());
        cosPolicyVo.setRegion(tencentCosConfig.getRegion());
        cosPolicyVo.setAuthorization(authorization);
        cosPolicyVo.setFilename(filename);
        cosPolicyVo.setExpire(expiration.getTime());
        return cosPolicyVo;
    }

    public String signPreviewAuthorization(String filename) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("ci-process", "doc-preview");
        paramMap.put("ObjectKey", filename);
        COSCredentials cosCredentials = new BasicCOSCredentials(tencentCosConfig.getSecretId(), tencentCosConfig.getSecretKey());
        COSSigner cosSigner = new COSSigner();
        return cosSigner.buildAuthorizationStr(HttpMethodName.GET, filename, null, paramMap, cosCredentials, new Date(System.currentTimeMillis() + tencentCosConfig.getExpireTime() * 1000L));
    }
}
