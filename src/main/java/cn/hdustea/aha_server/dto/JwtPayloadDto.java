package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * jwt token payload字段DTO
 *
 * @author STEA_YY
 **/
@Data
public class JwtPayloadDto {
    /**
     * 账号
     */
    private Integer account;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 是否签署服务协议
     */
    private boolean signedNotice;
    /**
     * 是否签署合同
     */
    private boolean signedContract;

    /**
     * 是否通过身份认证
     */
    private Integer authenticated;
}
