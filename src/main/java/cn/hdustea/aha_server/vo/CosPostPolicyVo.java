package cn.hdustea.aha_server.vo;

import lombok.Data;

/**
 * 腾讯云COS POST方法上传签名VO
 *
 * @author STEA_YY
 **/
@Data
public class CosPostPolicyVo {
    /**
     * 目标bucketName
     */
    private String bucketName;
    /**
     * 服务器地域
     */
    private String region;
    /**
     * 经过 Base64 编码的“策略”（Policy）内容
     */
    private String policy;

    /**
     * COS SecretId
     */
    private String secretId;

    /**
     * COS KeyTime
     */
    private String keyTime;

    /*
     * 上传签名
     */
    private String signature;

    /**
     * 完整文件名
     */
    private String filename;
}
