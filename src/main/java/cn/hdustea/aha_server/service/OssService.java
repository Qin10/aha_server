package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.AliyunOssConfig;
import cn.hdustea.aha_server.util.EncryptUtil;
import cn.hdustea.aha_server.vo.OssPolicyVo;
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
    private AliyunOssConfig aliyunOssConfig;

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
            bucketName = aliyunOssConfig.getPrivateBucketName();
        } else {
            bucketName = aliyunOssConfig.getPublicBucketName();
        }
        String host = "http://" + bucketName + "." + aliyunOssConfig.getEndpoint();
        Date expiration = new Date(new Date().getTime() + aliyunOssConfig.getExpireTime() * 1000L);
        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, aliyunOssConfig.getMaxSize());
        String policyStr = oss.generatePostPolicy(expiration, policyConditions);
        String policyBase64 = EncryptUtil.toBase64String(policyStr.getBytes());
        String signature = oss.calculatePostSignature(policyStr);
        OssPolicyVo ossPolicyVo = new OssPolicyVo();
        ossPolicyVo.setHost(host);
        ossPolicyVo.setAccessid(aliyunOssConfig.getAccessKeyId());
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
        Date expiration = new Date(new Date().getTime() + aliyunOssConfig.getExpireTime() * 1000L);
        return oss.generatePresignedUrl(aliyunOssConfig.getPrivateBucketName(), filename, expiration);
    }

    /**
     * 构建下载url
     *
     * @param filename oss内文件名
     * @return 下载url
     */
    public String buildPublicDownloadUrl(String filename) {
        return "https://" +
                aliyunOssConfig.getPublicBucketName() +
                "." +
                aliyunOssConfig.getEndpoint() +
                "/" +
                filename;
    }

    /**
     * 根据文件名改变项目资源冻结状态
     *
     * @param filename 文件名
     * @param freezed  是否冻结
     */
    public void freezeProjectResource(String filename, boolean freezed) {
        projectResourceService.freezeProjectResourceByFilename(filename, freezed);
    }

    /**
     * 校验OSS内容安全回调请求
     *
     * @param checksum OSS签名
     * @param content  OSS返回内容
     * @return 校验结果
     */
    public boolean verifyOssGreenCallback(String checksum, String content) {
        String payload = aliyunOssConfig.getAliyunUid() + aliyunOssConfig.getGreenCallbackSeed() + content;
        String generatedChecksum = EncryptUtil.getSha256(payload);
        return generatedChecksum.equals(checksum);
    }
}