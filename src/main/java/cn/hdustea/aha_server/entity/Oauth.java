package cn.hdustea.aha_server.entity;

import lombok.Data;

/**
 * 第三方授权凭证实体类
 *
 * @author STEA_YY
 **/
@Data
public class Oauth {
    private Integer id;

    private Integer userId;

    private String oauthType;

    private String oauthId;

    private String unionid;

    private String credential;
}