package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.userOperationLog.annotation.LogUserOperation;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试控制器
 *
 * @author STEA_YY
 **/

@RestController
public class TestController {
    @LogUserOperation("测试")
    @RequiresLogin
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/guji")
    public ResponseBean gujiWorld(@RequestParam("username") String username, @RequestParam("say") String say) {
        String response = username + "说：" + say;
        return new ResponseBean(200, response, null, TimeUtil.getFormattedTime(new Date()));
    }
}
