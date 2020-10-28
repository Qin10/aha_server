package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.OssPolicyBean;
import cn.hdustea.aha_server.config.AliyunOSSConfig;
import cn.hdustea.aha_server.util.JacksonUtil;
import cn.hdustea.aha_server.util.RestTemplateUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * oss服务类
 *
 * @author STEA_YY
 **/
@Service
public class OssService {
    @Resource
    private OSS oss;
    @Resource
    private AliyunOSSConfig aliyunOSSConfig;

    /**
     * 签名oss上传文件请求
     *
     * @param dir       资源上传路径
     * @param isPrivate 该资源是否为私有
     * @return oss相关信息
     */
    public OssPolicyBean signUpload(String dir, boolean isPrivate) {
        String bucketName;
        if (isPrivate) {
            bucketName = aliyunOSSConfig.getPrivateBucketName();
        } else {
            bucketName = aliyunOSSConfig.getPublicBucketName();
        }
        String host = "http://" + bucketName + "." + aliyunOSSConfig.getEndpoint();
        Date expiration = new Date(new Date().getTime() + aliyunOSSConfig.getExpireTime() * 1000);
        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        String policyStr = oss.generatePostPolicy(expiration, policyConditions);
        String policyBase64 = JacksonUtil.toBase64String(policyStr.getBytes());
        String signature = oss.calculatePostSignature(policyStr);
        OssPolicyBean ossPolicyBean = new OssPolicyBean();
        ossPolicyBean.setHost(host);
        ossPolicyBean.setAccessid(aliyunOSSConfig.getAccessKeyId());
        ossPolicyBean.setPolicy(policyBase64);
        ossPolicyBean.setDir(dir);
        ossPolicyBean.setSignature(signature);
        ossPolicyBean.setExpire(expiration.getTime());
        return ossPolicyBean;
    }

    /**
     * 签名下载OSS中的私有文件
     *
     * @param filename 文件名（包括路径）
     * @return 访问URL
     */
    public URL signDownload(String filename) {
        Date expiration = new Date(new Date().getTime() + aliyunOSSConfig.getExpireTime() * 1000);
        return oss.generatePresignedUrl(aliyunOSSConfig.getPrivateBucketName(), filename, expiration);
    }
}