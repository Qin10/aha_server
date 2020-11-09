package cn.hdustea.aha_server.util;

import cn.hdustea.aha_server.dto.WechatBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
    public static WechatBean getWxInfo(String code) {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>();
        // https://mp.weixin.qq.com/wxopen/devprofile?action=get_profile&token=164113089&lang=zh_CN
        //小程序appId
        requestUrlParam.put("appid", "wx9b49ada20b56111a");
        //小程序secret
        requestUrlParam.put("secret", "bdafdd48671aeb8ad63f5af73b40f332");
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(requestUrl, requestUrlParam, Map.class);
        Map<String, String> responseBody = mapResponseEntity.getBody();
        WechatBean wechatBean = new WechatBean();
        wechatBean.setOpenId(responseBody != null ? responseBody.get("openid") : null);
        wechatBean.setSessionKey(responseBody != null ? responseBody.get("session_key") : null);
        wechatBean.setUnionid(responseBody != null ? responseBody.get("unionid") : null);
        return wechatBean;
//        String response = HttpClientUtil.doPost(requestUrl, requestUrlParam);
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> wechatMap = mapper.readValue(response, Map.class);
//        return wechatMap.get("openid");
    }
}
