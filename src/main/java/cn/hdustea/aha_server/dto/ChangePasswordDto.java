package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 修改密码请求vo
 *
 * @author STEA_YY
 **/
@Data
public class ChangePasswordDto {
    /**
     * 新密码
     */
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
    /**
     * 短信验证码
     */
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
