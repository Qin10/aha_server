package cn.hdustea.aha_server.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 上传服务端签名和回调DTO
 *
 * @author STEA_YY
 **/
@Data
public class OssPolicyBean {
    /**
     * 阿里云accessid
     */
    private String accessid;
    /**
     * oss服务器地址
     */
    private String host;
    /**
     * 上传保险
     */
    private String policy;
    /**
     * 上传签名
     */
    private String signature;
    /**
     * 签名过期时间戳
     */
    private long expire;
    /**
     * 上传路径(文件前缀)
     */
    private String dir;
}
