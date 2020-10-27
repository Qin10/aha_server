package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * jwt token payload字段的封装实体类
 *
 * @author STEA_YY
 **/
@Data
public class JwtPayloadBean {
    private String account;
    private String roleName;
    private boolean isSignedNotice;
}
