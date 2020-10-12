package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.UserDao;
import cn.hdustea.aha_server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务类
 *
 * @author STEA_YY
 **/
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public User getUserByPhone(String phone) {
        return userDao.findUserByPhone(phone);
    }

    public int saveUser(User user) {
        return userDao.saveUser(user);
    }
}
