package cn.hdustea.aha_server.dto;

import cn.hdustea.aha_server.util.ValidationUtil;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = ValidationUtil.MOBILE,message = "手机号格式错误！")
    @NotEmpty(message = "手机号不能为空！")
    private String phone;
    /**
     * 验证码
     */
    @NotEmpty(message = "短信验证码不能为空！")
    private String code;
}
