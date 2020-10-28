package cn.hdustea.aha_server.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 用户登录数据的Javabean
 *
 * @author STEA_YY
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    @NotEmpty(message = "手机号不能为空！")
    private String phone;
    @NotEmpty(message = "密码不能为空！")
    private String password;
}
