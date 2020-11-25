package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.exception.apiException.ForbiddenException;
import cn.hdustea.aha_server.util.EncryptUtil;
import cn.hdustea.aha_server.vo.OssPolicyVo;
import cn.hdustea.aha_server.config.AliyunOSSConfig;
import cn.hdustea.aha_server.util.JacksonUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
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
    private ProjectResourceService projectResourceService;
    @Resource
    private AliyunOSSConfig aliyunOSSConfig;

    /**
     * 签名oss上传文件请求
     *
     * @param dir       资源上传路径
     * @param isPrivate 该资源是否为私有
     * @return oss相关信息
     */
    public OssPolicyVo signUpload(String dir, boolean isPrivate) {
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
        OssPolicyVo ossPolicyVo = new OssPolicyVo();
        ossPolicyVo.setHost(host);
        ossPolicyVo.setAccessid(aliyunOSSConfig.getAccessKeyId());
        ossPolicyVo.setPolicy(policyBase64);
        ossPolicyVo.setDir(dir);
        ossPolicyVo.setSignature(signature);
        ossPolicyVo.setExpire(expiration.getTime());
        return ossPolicyVo;
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

    /**
     * 构建下载url
     *
     * @param filename oss内文件名
     * @return 下载url
     */
    public String buildPublicDownloadUrl(String filename) {
        return "https://" +
                aliyunOSSConfig.getPublicBucketName() +
                "." +
                aliyunOSSConfig.getEndpoint() +
                "/" +
                filename;
    }

    public void freezeProjectResource(String filename, boolean freezed) {
        projectResourceService.freezeProjectResourceByFilename(filename, freezed);
    }

    public boolean verifyOssGreenCallback(String checksum, String content) {
        String payload = aliyunOSSConfig.getAliyunUid() + aliyunOSSConfig.getGreenCallbackSeed() + content;
        String generatedChecksum = EncryptUtil.getSHA256(payload);
        return generatedChecksum.equals(checksum);
    }
}