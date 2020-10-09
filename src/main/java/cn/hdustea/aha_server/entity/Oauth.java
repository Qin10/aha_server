package cn.hdustea.aha_server.entity;

import lombok.Data;

/**
 * @program: aha_server
 * @description: 第三方Oauth2登录数据的实体类
 * @author: HduStea_YY
 * @create: 2020-10-09 16:09
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
