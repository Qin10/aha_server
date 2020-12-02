package cn.hdustea.aha_server.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import java.util.Map;

/**
 * Jackson相关工具类
 *
 * @author STEA_YY
 **/
public class JacksonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JacksonUtil() {

    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * javaBean、列表数组转换为json字符串
     */
    public static String obj2json(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * json 转JavaBean
     */

    public static <T> T json2pojo(String jsonString, Class<T> clazz) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(jsonString, clazz);
    }

    /**
     * json字符串转换为map
     */
    public static <T> Map<String, Object> json2map(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString, Map.class);
    }

    /**
     * 二进制数据转base64
     *
     * @param binaryData 二进制数据
     * @return base64字符串
     */
    public static String toBase64String(byte[] binaryData) {
        return new String(Base64.encodeBase64(binaryData));
    }
}