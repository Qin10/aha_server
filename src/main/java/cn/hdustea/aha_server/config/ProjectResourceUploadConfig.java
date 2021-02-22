package cn.hdustea.aha_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目资源上传配置类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("project-resource-upload")
@Data
public class ProjectResourceUploadConfig {
    private Long tokenExpireTime;
}
