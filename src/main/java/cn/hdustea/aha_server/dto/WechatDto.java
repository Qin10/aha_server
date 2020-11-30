package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 微信小程序鉴权相关DTO
 *
 * @author STEA_YY
 **/
@Data
public class WechatDto {

    /**
     * 用户openId
     */
    private String openid;

    /**
     * 用户session key
     */
    private String session_key;

    /**
     * 用户unionid
     */
    private String unionid;

    /**
     * 错误码
     */
    private int errcode;

    /**
     * 错误信息
     */
    private String errmsg;

}
