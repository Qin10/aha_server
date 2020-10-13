package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.UserDao;
import cn.hdustea.aha_server.entity.User;
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

    /**
     * 根据手机号查询用户实体
     *
     * @param phone 手机号
     * @return 用户实体类
     */
    public User getUserByPhone(String phone) {
        return userDao.findUserByPhone(phone);
    }

    /**
     * 保存用户实体
     *
     * @param user 用户实体类
     */
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    /**
     *
     * @param phone 手机号
     * @param newPassword 新的密码
     */
    public void updatePassword(String phone, String newPassword) {
        userDao.updatePassword(phone, newPassword);
    }
}
