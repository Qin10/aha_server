package cn.hdustea.aha_server.entity;

import lombok.Data;

/**
 * 第三方Oauth2方式登录数据的实体类
 *
 * @author STEA_YY
 **/
@Data
public class Oauth {
    private int id;
    private int userId;
    private String oauthType;
    private String oauthId;
    private String unionid;
    private String credential;
    private User user;
}
