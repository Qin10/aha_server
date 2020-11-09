package cn.hdustea.aha_server.dto;

import cn.hdustea.aha_server.entity.UserInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 登录用户获取个人信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class PersonalUserInfoBean {
    private String phone;
    private boolean signedNotice;
    private boolean signedContract;
    private BigDecimal contribPoint;
    private UserInfo userInfo;
}
