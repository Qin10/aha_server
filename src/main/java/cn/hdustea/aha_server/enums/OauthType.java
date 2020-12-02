package cn.hdustea.aha_server.enums;

import lombok.Getter;

/**
 * 授权类型枚举
 *
 * @author STEA_YY
 **/
@Getter
public enum OauthType {

    /**
     * 手机号授权
     */
    PHONE("phone"),
    /**
     * 微信授权
     */
    WECHAT("wechat");

    private final String value;

    OauthType(String value) {
        this.value = value;
    }
}
