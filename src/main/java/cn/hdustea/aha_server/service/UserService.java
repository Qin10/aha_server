package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.UserDao;
import cn.hdustea.aha_server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: aha_server
 * @description: 用户服务类
 * @author: HduStea_YY
 * @create: 2020-10-10 00:48
 **/
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUserByPhone(String phone) {
        return userDao.findUserByPhone(phone);
    }

    public int saveUser(User user) {
        return userDao.saveUser(user);
    }
}
