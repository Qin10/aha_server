package cn.hdustea.aha_server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件上传路径配置类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("file-upload-path")
@Data
public class FileUploadPathConfig {
    private String avatarPath;
}
