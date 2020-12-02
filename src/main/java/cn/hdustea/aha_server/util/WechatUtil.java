package cn.hdustea.aha_server.util;

import cn.hdustea.aha_server.dto.WechatDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 微信小程序鉴权授权相关工具类
 *
 * @author STEA_YY
 **/
public class WechatUtil {
    /**
     * 传入微信请求code返回授权校验结果
     *
     * @param code 微信请求code
     * @return 包含了各种校验信息的Map
     */
    public static WechatDto getWxInfo(String code, String appid, String secret) throws Exception {
        //发送post请求读取调用微信接口获取openid用户唯一标识
        RestTemplate restTemplate = new RestTemplate();
        String requestUrlBuilder = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appid + "&secret=" + secret + "&grant_type=authorization_code&js_code=" +
                code;
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrlBuilder, null, String.class);
        String responseStr = responseEntity.getBody();
        return JacksonUtil.json2pojo(responseStr, WechatDto.class);
    }
}