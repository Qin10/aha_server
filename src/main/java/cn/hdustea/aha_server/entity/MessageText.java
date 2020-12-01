package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class MessageText {
    private Integer id;

    /**
     * 发送者id
     */
    private Integer senderUserId;

    /**
     * 信件内容
     */
    private String content;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 发件日期
     */
    private Date postDate;
}