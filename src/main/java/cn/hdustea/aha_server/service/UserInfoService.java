package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.UserInfoDao;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: aha_server
 * @description: 用户详细信息服务类
 * @author: HduStea_YY
 * @create: 2020-10-10 11:30
 **/
@Service
public class UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    public UserInfo getUserInfoByUserId(int userId) {
        return userInfoDao.findUserInfoByUserId(userId);
    }

    public void saveUserInfo(UserInfo userInfo) throws DaoException {
        UserInfo possibleUserInfo = userInfoDao.findUserInfoById(userInfo.getUserId());
        if (possibleUserInfo == null) {
            userInfoDao.saveUserInfo(userInfo);
        } else {
            throw new DaoException("用户详情已存在，无法插入！");
        }
    }

    public void deleteUserInfoById(int id) throws DaoException {
        UserInfo userInfo = userInfoDao.findUserInfoById(id);
        if (userInfo != null) {
            userInfoDao.deleteUserInfo(id);
        } else {
            throw new DaoException("不存在对应记录，删除用户详情失败！");
        }
    }
}
