package cn.hdustea.aha_server;

import cn.hdustea.aha_server.entity.ResourceInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ResourceInfoMapper;
import cn.hdustea.aha_server.mapper.ResourceMapper;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.ResourceService;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.service.UserService;
import cn.hdustea.aha_server.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private OssService ossService;
    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ResourceMapper resourceMapper;
    @Resource
    private ResourceInfoMapper resourceInfoMapper;
    @Resource
    private ResourceService resourceService;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetUser() throws SelectException {
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
    void testRedis() {
        redisUtil.incr("testaaaa", 1);
        System.out.println(redisUtil.get("testaaaa"));
    }

    @Test
    void testResourceAdd() {
        resourceMapper.updateReadById(100, 1);
    }

    @Test
    void testResourceMapper() {
        System.out.println(resourceInfoMapper.selectByResId(4));
    }
    @Test
    void testCollect() throws SelectException {
        System.out.println(resourceService.getAllCollectionByPhone("15382355341"));
    }
}
