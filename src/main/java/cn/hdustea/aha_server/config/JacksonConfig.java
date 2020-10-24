package cn.hdustea.aha_server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * jackson序列化相关配置类
 *
 * @author STEA_YY
 **/
@Configuration
public class JacksonConfig implements WebMvcConfigurer, InitializingBean {
    @Resource
    private ObjectMapper obj;

    private SimpleModule getSimpleModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(ObjectId.class, ToStringSerializer.instance);
        return simpleModule;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (obj != null) {
            SimpleModule simpleModule = getSimpleModule();
            obj.registerModule(simpleModule);
        }
    }

}
