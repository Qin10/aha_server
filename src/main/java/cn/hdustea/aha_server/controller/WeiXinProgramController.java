package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.service.WechatProgramService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: aha_server
 * @description: 微信小程序授权/鉴权控制器
 * @author: HduStea_YY
 * @create: 2020-10-10 03:00
 **/
@RestController
public class WeiXinProgramController {
    @Autowired
    private WechatProgramService wechatProgramService;
    @PostMapping("/wxLogin")
    public ResponseBean wechatLogin(@RequestParam("code") String code) throws Exception {
        String token = wechatProgramService.wechatLogin(code);
        return new ResponseBean(200, "登录成功", token, TimeUtil.getFormattedTime(new Date()));
    }
}
