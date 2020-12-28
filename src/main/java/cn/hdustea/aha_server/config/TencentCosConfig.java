package cn.hdustea.aha_server.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 腾讯云COS配置类
 *
 * @author STEA_YY
 **/
@ConfigurationProperties("tencent-cos")
@Data
public class TencentCosConfig {
    private String secretId;
    private String secretKey;
    private String region;
    private long expireTime;
    private String publicBucketName;
    private String resourceBucketName;
    private String documentConvertQueueId;

    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region regionConfig = new Region(region);
        ClientConfig clientConfig = new ClientConfig(regionConfig);
        return new COSClient(cred, clientConfig);
    }
}
