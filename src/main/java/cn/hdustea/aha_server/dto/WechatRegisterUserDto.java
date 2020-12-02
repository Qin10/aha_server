package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 微信注册信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class WechatRegisterUserDto {
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
     * 微信code
     */
    @NotEmpty(message = "code不能为空")
    private String code;
}
