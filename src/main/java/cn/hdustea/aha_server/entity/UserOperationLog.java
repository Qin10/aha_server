package cn.hdustea.aha_server.entity;

import java.util.Date;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class UserOperationLog {
    private Integer id;

    private String account;

    private String operation;

    private Long time;

    private String method;

    private String params;

    private String ip;

    private Date createTime;
}