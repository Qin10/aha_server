package cn.hdustea.aha_server.controller;


import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: aha_server
 * @description: 测试控制器
 * @author: HduStea_YY
 * @create: 2020-10-10 02:28
 **/

@RestController
public class TestController {

    @RequiresAuthentication
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
}
