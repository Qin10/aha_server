package cn.hdustea.aha_server.entity;

import java.util.Date;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class MessageText {
    private Integer id;

    /**
    * 发送者手机号
    */
    private String senderPhone;

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