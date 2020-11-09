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
    private String accessid;
    private String host;
    private String policy;
    private String signature;
    private long expire;
    private String dir;
}
