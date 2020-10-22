package cn.hdustea.aha_server;

import cn.hdustea.aha_server.config.AliyunOSSConfig;
import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.config.JWTConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Springboot启动类
 *
 * @author STEA_YY
 */
@SpringBootApplication
@EnableConfigurationProperties({JWTConfig.class, FileUploadPathConfig.class, AliyunOSSConfig.class})

public class AhaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AhaServerApplication.class, args);
    }

}
