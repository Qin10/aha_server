package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 绑定手机号Dto
 *
 * @author STEA_YY
 **/
@Data
public class PhoneBindDto {
    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空！")
    private String phone;
    /**
     * 验证码
     */
    @NotEmpty(message = "短信验证码不能为空！")
    private String code;
}
