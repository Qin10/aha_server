package cn.hdustea.aha_server.entity;

import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class Oauth {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 授权类型
     */
    private String oauthType;

    /**
     * 授权码
     */
    private String oauthId;

    /**
     * 微信通用id(保留字段，考虑废弃)
     */
    private String unionid;

    /**
     * 授权秘钥(保留字段)
     */
    private String credential;
}