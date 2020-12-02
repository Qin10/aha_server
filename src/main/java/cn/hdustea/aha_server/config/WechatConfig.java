package cn.hdustea.aha_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信小程序配置类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("wechat")
@Data
public class WechatConfig {
    private String appid;
    private String secret;
}
