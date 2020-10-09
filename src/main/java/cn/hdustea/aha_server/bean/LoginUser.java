package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * @program: aha_server
 * @description: 用户登录数据的Javabean
 * @author: HduStea_YY
 * @create: 2020-10-09 22:47
 **/
@Data
public class LoginUser {
    private String account;
    private String password;
}
