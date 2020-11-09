package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * jwt token payload字段DTO
 *
 * @author STEA_YY
 **/
@Data
public class JwtPayloadBean {
    private String account;
    private String roleName;
    private boolean signedNotice;
    private boolean signedContract;
}
