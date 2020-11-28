package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 站内信DTO
 *
 * @author STEA_YY
 **/
@Data
public class MessageDto {
    private String reveiverPhone;
    private Integer type;
    private int textId;
    @NotEmpty(message = "站内信内容不能为空！")
    private String content;
}