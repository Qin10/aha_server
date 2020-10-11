package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.service.WechatProgramService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * @program: aha_server
 * @description: 微信小程序授权/鉴权控制器
 * @author: HduStea_YY
 * @create: 2020-10-10 03:00
 **/

@RestController
public class WechatProgramController {
    @Autowired
    private WechatProgramService wechatProgramService;

    @PostMapping("/wxLogin")
    public ResponseBean wechatLogin(@RequestParam("code") String code) throws Exception {
        String token = wechatProgramService.wechatLogin(code);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("token", token);
        return new ResponseBean(200, "登录成功", responseMap, TimeUtil.getFormattedTime(new Date()));
    }
}
