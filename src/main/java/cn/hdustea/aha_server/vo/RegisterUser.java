package cn.hdustea.aha_server.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 注册请求vo
 *
 * @author STEA_YY
 **/
@Data
public class RegisterUser {
    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;
    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    private String nickname;
    /**
     * 是否同意服务协议
     */
    private boolean signedNotice;
    /**
     * 短信验证码
     */
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
