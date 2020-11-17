package cn.hdustea.aha_server;

import cn.hdustea.aha_server.config.AliyunOSSConfig;
import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.config.JWTConfig;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
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
@EnableConfigurationProperties({JWTConfig.class, FileUploadPathConfig.class, AliyunOSSConfig.class, UserOperationLogConfig.class})
@MapperScan(basePackages = "cn.hdustea.aha_server.mapper")
@EnableScheduling
public class AhaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AhaServerApplication.class, args);
//        buildApiDoc();
    }

//    private static void buildApiDoc() {
//        DocsConfig config = new DocsConfig();
//        config.setProjectPath("C:\\Users\\YY-PC\\IdeaProjects\\aha_server"); // 项目根目录
//        config.setProjectName("aha-server"); // 项目名称
//        config.setApiVersion("V1.0");       // 声明该API的版本
//        config.setDocsPath("api_doc"); // 生成API 文档所在目录
//        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
//        config.addPlugin(new MarkdownDocPlugin());  //导出md格式文档
//        Docs.buildHtmlDocs(config); // 执行生成文档
//    }
}