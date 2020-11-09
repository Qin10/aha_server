package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 微信小程序鉴权相关DTO
 *
 * @author STEA_YY
 **/
@Data
public class WechatBean {
    /**
     * 用户openId
     */
    private String openId;
    /**
     * 用户session key
     */
    private String sessionKey;
    /**
     * 用户unionid
     */
    private String unionid;
}
