package cn.hdustea.aha_server;

import cn.hdustea.aha_server.dao.UserDao;
import cn.hdustea.aha_server.dao.UserInfoDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AhaServerApplicationTests {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserInfoDao userInfoDao;

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
    void testUserInfo(){
        userInfoDao.updateAvatarFilename("gugugugu","14");
    }
}
