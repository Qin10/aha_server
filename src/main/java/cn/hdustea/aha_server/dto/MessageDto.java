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

    /**
     * 收件人用户id
     */
    private Integer receiverUserId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 信件内容id(外键)
     */
    private int textId;

    /**
     * 信件标题
     */
    private String title;
    /**
     * 信件内容
     */
    @NotEmpty(message = "站内信内容不能为空！")
    private String content;
}