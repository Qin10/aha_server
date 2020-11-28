package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 站内信VO
 *
 * @author STEA_YY
 **/
@Data
public class MessageVo {
    private int id;
    private String senderPhone;
    private int type;
    private int status;
    private Date receiveDate;
    private Date postDate;
    private String content;
}