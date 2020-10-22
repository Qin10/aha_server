package cn.hdustea.aha_server;

import cn.hdustea.aha_server.dao.UserDao;
import cn.hdustea.aha_server.dao.UserInfoDao;
import cn.hdustea.aha_server.service.OssService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private UserDao userDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private OssService ossService;

    @Test
    void contextLoads() {
    }

    @Test
    void testSelectUser() {
        System.out.println(userDao.findAllUsers());
    }

    @Test
    void testInfoDao() {
        System.out.println(userInfoDao.findUserInfoByUserId(1));
    }

    @Test
    void testUserInfo() {
        userInfoDao.updateAvatarFilename("gugugugu", "14");
    }

    @Test
    void testAliOssUpload() throws Exception {
        System.out.println(ossService.signUpload("abc/", "userInfo/avatar", "15382355341"));
    }
}
