package cn.hdustea.aha_server;

import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private OssService ossService;
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetUser(){
        System.out.println(userService.getUserByPhone("15382355341"));
    }


    @Test
    void testAliOssUpload(){
        System.out.println(ossService.signUpload("abc/",false));
    }
}
