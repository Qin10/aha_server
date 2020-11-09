package cn.hdustea.aha_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 包含了token和用户信息的DTO
 *
 * @author STEA_YY
 **/
@Data
@AllArgsConstructor
public class TokenAndPersonalUserInfoBean {
    private String token;
    private PersonalUserInfoBean personalUserInfo;
}
