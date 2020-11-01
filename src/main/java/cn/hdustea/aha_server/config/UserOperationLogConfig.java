package cn.hdustea.aha_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用户行为日志配置类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("user-operation-log")
@Data
public class UserOperationLogConfig {
    private String format;
}
