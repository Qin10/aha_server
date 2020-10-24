package cn.hdustea.aha_server;

import cn.hdustea.aha_server.service.OssService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private OssService ossService;

    @Test
    void contextLoads() {
    }


    @Test
    void testAliOssUpload(){
        System.out.println(ossService.signUpload("abc/"));
    }
}
