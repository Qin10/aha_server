package cn.hdustea.aha_server.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录数据的Javabean
 *
 * @author STEA_YY
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String phone;
    private String password;
}
