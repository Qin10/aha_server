package cn.hdustea.aha_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt配置文件类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("jwt-config")
@Data
public class JWTConfig {
    private String secret;
    private int expireTime;
    private int refreshTokenExpireTime;
}
