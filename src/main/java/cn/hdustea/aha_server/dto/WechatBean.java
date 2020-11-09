package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 微信小程序鉴权相关DTO
 *
 * @author STEA_YY
 **/
@Data
public class WechatBean {
    private String openId;
    private String sessionKey;
    private String unionid;
}
