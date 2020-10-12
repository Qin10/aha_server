package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * 注册时使用的Javabean
 *
 * @author STEA_YY
 **/
@Data
public class RegisterUser {
    private String phone;
    private String password;
    private String nickname;
    private String code;
}
