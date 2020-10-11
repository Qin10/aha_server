package cn.hdustea.aha_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: aha_server
 * @description: 图片路径配置类
 * @author: HduStea_YY
 * @create: 2020-10-11 17:31
 **/
@Configuration
public class ImgConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:" + "C:\\server\\avatar\\");
    }
}