package cn.hdustea.aha_server.bean;

import cn.hdustea.aha_server.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 包含了token和用户信息的Javabean
 *
 * @author STEA_YY
 **/
@Data
@AllArgsConstructor
public class TokenAndPersonalUserInfoBean {
    private String token;
    private PersonalUserInfoBean personalUserInfo;
}
