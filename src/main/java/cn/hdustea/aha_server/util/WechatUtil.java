package cn.hdustea.aha_server.util;

import cn.hdustea.aha_server.dto.WechatDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 微信小程序鉴权授权相关工具类
 *
 * @author STEA_YY、cygao
 **/
public class WechatUtil {

    private static final String WECHAT_AUTH_URL_FORMAT = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&grant_type=authorization_code&js_code=%s";

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    /**
     * 传入微信请求code返回授权校验结果
     *
     * @param code 微信请求code
     * @return 包含了各种校验信息的Map
     */
    public static WechatDto getWxInfo(String code, String appid, String secret) throws JsonProcessingException {
        //发送post请求读取调用微信接口获取openid用户唯一标识
        String requestUrl = String.format(WECHAT_AUTH_URL_FORMAT, appid, secret, code);
        ResponseEntity<String> responseEntity = REST_TEMPLATE.postForEntity(requestUrl, null, String.class);
        String responseStr = responseEntity.getBody();
        return JacksonUtil.jsonToPojo(responseStr, WechatDto.class);
    }
}