package cn.hdustea.aha_server;

import cn.hdustea.aha_server.service.OssService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    void testAliOssUpload() throws Exception {
        System.out.println(ossService.signUpload("abc/", "userInfo/avatar", "15382355341"));
    }
}
