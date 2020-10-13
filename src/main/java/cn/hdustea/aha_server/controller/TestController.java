package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author STEA_YY
 **/

@RestController
public class TestController {

    @RequiresLogin
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
}
