package cn.hdustea.aha_server.userOperationLog.entity;

import lombok.Data;

import java.util.Date;

/**
 * 系统日志实体类
 *
 * @author STEA_YY
 */
@Data
public class UserOperationLog {
    private Integer id;
    private String account;
    private String operation;
    private Integer time;
    private String method;
    private String params;
    private String ip;
    private Date createTime;
}