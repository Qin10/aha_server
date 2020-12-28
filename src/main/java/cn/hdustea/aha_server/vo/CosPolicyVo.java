package cn.hdustea.aha_server.vo;

import lombok.Data;

/**
 * 腾讯云COS上传签名VO
 *
 * @author STEA_YY
 **/
@Data
public class CosPolicyVo {
    /**
     * 目标bucketName
     */
    private String bucketName;
    /**
     * 服务器地域
     */
    private String region;
    /**
     * 上传签名
     */
    private String authorization;
    /**
     * 签名过期时间戳
     */
    private long expire;
    /**
     * 完整文件名
     */
    private String filename;
}
