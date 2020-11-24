package cn.hdustea.aha_server.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 阿里云oss的配置类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("aliyun-oss")
@Data
public class AliyunOSSConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String publicBucketName;
    private String privateBucketName;
    private int expireTime;
    private int maxSize;

    @Bean
    public OSS OSS() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @Bean
    public IAcsClient iAcsClient() {
        return new DefaultAcsClient(DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}