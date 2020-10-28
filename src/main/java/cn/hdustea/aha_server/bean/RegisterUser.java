package cn.hdustea.aha_server.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 注册时使用的Javabean
 *
 * @author STEA_YY
 **/
@Data
public class RegisterUser {
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "昵称不能为空")
    private String nickname;
    private boolean signedNotice;
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
