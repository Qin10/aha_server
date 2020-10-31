package cn.hdustea.aha_server;

import cn.hdustea.aha_server.config.AliyunOSSConfig;
import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.config.JWTConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Springboot启动类
 *
 * @author STEA_YY
 */
@SpringBootApplication
@EnableConfigurationProperties({JWTConfig.class, FileUploadPathConfig.class, AliyunOSSConfig.class})
@MapperScan(basePackages = "cn.hdustea.aha_server.mapper")
@EnableScheduling
public class AhaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AhaServerApplication.class, args);
    }

}
