package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * @program: aha_server
 * @description: 微信小程序鉴权相关JavaBean
 * @author: HduStea_YY
 * @create: 2020-10-10 03:44
 **/
@Data
public class WechatBean {
    private String openId;
    private String sessionKey;
    private String unionid;
}
