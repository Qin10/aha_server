package cn.hdustea.aha_server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 包含了token和用户信息的DTO
 *
 * @author STEA_YY
 **/
@Data
@AllArgsConstructor
public class LoginInfoVo {
    /**
     * token令牌
     */
    private String token;
    /**
     * 用户信息
     */
    private PersonalUserInfoVo personalUserInfo;
}
