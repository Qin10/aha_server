package cn.hdustea.aha_server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 站内信VO
 *
 * @author STEA_YY
 **/
@Data
public class MessageVo {
    /**
     * 站内信id
     */
    private int id;

    /**
     * 发信人手机号
     */
    private String senderPhone;

    /**
     * 类型
     */
    private int type;

    /**
     * 阅读状态
     */
    private int status;

    /**
     * 收件时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;

    /**
     * 信件内容
     */
    private String content;
}