package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * @program: aha_server
 * @description: 注册时使用的Javabean
 * @author: HduStea_YY
 * @create: 2020-10-11 21:28
 **/
@Data
public class RegisterUser {
    private String phone;
    private String password;
    private String nickname;
    private String code;
}
