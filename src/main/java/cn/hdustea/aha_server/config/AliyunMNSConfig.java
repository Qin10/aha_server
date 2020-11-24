package cn.hdustea.aha_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云消息队列配置类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("aliyun-mns")
@Data
public class AliyunMNSConfig {
    private String endpoint;
    private String documentConvertTopic;
}