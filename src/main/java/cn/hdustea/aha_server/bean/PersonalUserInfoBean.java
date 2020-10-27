package cn.hdustea.aha_server.bean;

import cn.hdustea.aha_server.entity.UserInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 登录用户获取个人信息的封装Javabean
 *
 * @author STEA_YY
 **/
@Data
public class PersonalUserInfoBean {
    private String phone;
    private boolean signedNotice;
    private BigDecimal contribPoint;
    private UserInfo userInfo;
}
