package cn.hdustea.aha_server.enums;

import lombok.Getter;

/**
 * 授权类型枚举
 *
 * @author STEA_YY
 **/
@Getter
public enum OauthType {

    WECHAT("wechat");

    private final String value;

    OauthType(String value) {
        this.value = value;
    }
}
