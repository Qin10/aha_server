package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.TencentCosConfig;
import cn.hdustea.aha_server.util.EncryptUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import cn.hdustea.aha_server.vo.CosPolicyVo;
import cn.hdustea.aha_server.vo.CosPostPolicyVo;
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
    private TencentCosConfig tencentCosConfig;

    @SuppressWarnings("unused")
    public CosPolicyVo signPutAuthorization(String filename, String bucketName) {
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

    public CosPolicyVo signPreviewAuthorization(String filename) {
        Date expiration = new Date(System.currentTimeMillis() + tencentCosConfig.getExpireTime() * 1000L);
        HashMap<String, String> paramMap = new HashMap<>();
        HashMap<String, String> headerMap = new HashMap<>();
        paramMap.put("ci-process", "doc-preview");
        COSCredentials cosCredentials = new BasicCOSCredentials(tencentCosConfig.getSecretId(), tencentCosConfig.getSecretKey());
        COSSigner cosSigner = new COSSigner();
        String authorization = cosSigner.buildAuthorizationStr(HttpMethodName.GET, filename, headerMap, paramMap, cosCredentials, expiration);
        CosPolicyVo cosPolicyVo = new CosPolicyVo();
        cosPolicyVo.setBucketName(tencentCosConfig.getResourceBucketName());
        cosPolicyVo.setRegion(tencentCosConfig.getRegion());
        cosPolicyVo.setAuthorization(authorization);
        cosPolicyVo.setFilename(filename);
        cosPolicyVo.setExpire(expiration.getTime());
        return cosPolicyVo;
    }

    public CosPostPolicyVo signPostAuthorization(String filename, String bucketName) {
        COSSigner cosSigner = new COSSigner();
        long now = System.currentTimeMillis();
        long startTimestamp = now / 1000L;
        long endTimestamp = startTimestamp + tencentCosConfig.getExpireTime();
        String keyTime = startTimestamp + ";" + endTimestamp;
        String policy = "{\n" +
                "    \"expiration\": \"" + TimeUtil.getISO8601Timestamp(endTimestamp * 1000L) + "\",\n" +
                "    \"conditions\": [\n" +
                "        { \"bucket\": \"" + bucketName + "\" },\n" +
                "        { \"key\": \"" + filename + "\" },\n" +
                "        { \"q-sign-algorithm\": \"sha1\" },\n" +
                "        { \"q-ak\": \"" + tencentCosConfig.getSecretId() + "\" },\n" +
                "        { \"q-sign-time\":\"" + keyTime + "\" }\n" +
                "    ]\n" +
                "}";
        String encodedPolicy = EncryptUtil.toBase64String(policy.getBytes());
        String signature = cosSigner.buildPostObjectSignature(tencentCosConfig.getSecretKey(), keyTime, policy);
        CosPostPolicyVo cosPostPolicyVo = new CosPostPolicyVo();
        cosPostPolicyVo.setRegion(tencentCosConfig.getRegion());
        cosPostPolicyVo.setBucketName(bucketName);
        cosPostPolicyVo.setKeyTime(keyTime);
        cosPostPolicyVo.setPolicy(encodedPolicy);
        cosPostPolicyVo.setSignature(signature);
        cosPostPolicyVo.setSecretId(tencentCosConfig.getSecretId());
        cosPostPolicyVo.setFilename(filename);
        return cosPostPolicyVo;
    }
}
