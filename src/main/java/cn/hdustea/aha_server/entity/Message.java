package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class Message {
    private Integer id;

    /**
     * 收件人id
     */
    private Integer receiverUserId;

    /**
     * 信件内容id(外键)
     */
    private Integer textId;

    /**
     * 阅读状态
     */
    private Integer status;

    /**
     * 收件时间
     */
    private Date receiveDate;
}