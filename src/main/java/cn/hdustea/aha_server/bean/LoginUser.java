package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * 用户登录数据的Javabean
 *
 * @author STEA_YY
 **/
@Data
public class LoginUser {
    private String phone;
    private String password;
}
