package cn.hdustea.aha_server.dto;

import cn.hdustea.aha_server.constants.RegexConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 注册请求vo
 *
 * @author STEA_YY
 **/
@Data
public class PhoneRegisterUserDto {
    /**
     * 手机号
     */
    @Pattern(regexp = RegexConstants.MOBILE, message = "手机号格式错误！")
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
    @Length(min = 4, max = 12, message = "昵称长度不符合要求")
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
