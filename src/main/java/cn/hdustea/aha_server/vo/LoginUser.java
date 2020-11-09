package cn.hdustea.aha_server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 登录请求vo
 *
 * @author STEA_YY
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空！")
    private String phone;
    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空！")
    private String password;
}
