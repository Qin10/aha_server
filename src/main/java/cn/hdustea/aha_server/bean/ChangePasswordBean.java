package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * 修改密码使用的Javabean
 *
 * @author STEA_YY
 **/
@Data
public class ChangePasswordBean {
    private String newPassword;
    private String code;
}
