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
    private int id;
    private String senderPhone;
    private int type;
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;
    private String content;
}