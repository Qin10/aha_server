package cn.hdustea.aha_server.bean;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改密码使用的Javabean
 *
 * @author STEA_YY
 **/
@Data
public class ChangePasswordBean {
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
