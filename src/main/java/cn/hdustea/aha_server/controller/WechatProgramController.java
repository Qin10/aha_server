package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.service.WechatProgramService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * 微信小程序授权/鉴权控制器
 *
 * @author STEA_YY
 **/

@RestController
public class WechatProgramController {
    @Resource
    private WechatProgramService wechatProgramService;

    /**
     * 通过微信小程序授权登录的接口
     *
     * @param code 小程序请求码
     * @throws Exception 向上抛出异常
     */
    @PostMapping("/wxLogin")
    public ResponseBean wechatLogin(@RequestParam("code") String code) throws Exception {
        String token = wechatProgramService.wechatLogin(code);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("token", token);
        return new ResponseBean(200, "登录成功", responseMap, TimeUtil.getFormattedTime(new Date()));
    }
}
