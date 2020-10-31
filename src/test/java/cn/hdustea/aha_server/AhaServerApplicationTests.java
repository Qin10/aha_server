package cn.hdustea.aha_server;

import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.UserService;
import cn.hdustea.aha_server.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private OssService ossService;
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetUser() {
        System.out.println(userService.getUserByPhone("15382355341"));
    }

    @Test
    void testAliOssUpload() {
        System.out.println(ossService.signUpload("abc/", false));
    }

    @Test
    void testOssBuildUrl() {
        System.out.println(ossService.buildPublicDownloadUrl("avatar/15677751219/1603972075808/avatar.JPG"));
    }

    @Test
    void testRedis(){
        redisUtil.incr("testaaaa",1);
        System.out.println(redisUtil.get("testaaaa"));
    }
}
