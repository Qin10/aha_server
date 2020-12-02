package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 发送短信DTO
 *
 * @author STEA_YY
 **/
@Data
public class SmsSendDto {
    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空！")
    private String phone;
    /**
     * 验证短信类型。取值register、changePassword、bindPhone
     */
    @NotEmpty(message = "短信不能为空！")
    private String type;
}
