package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.UserDao;
import cn.hdustea.aha_server.dao.UserInfoDao;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.DaoException;
import cn.hdustea.aha_server.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 用户详细信息服务类
 *
 * @author STEA_YY
 **/
@Service
public class UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    private static final String AVATAR_PATH = "C:\\server\\avatar\\";

    public UserInfo getUserInfoByUserId(int userId) {
        return userInfoDao.findUserInfoByUserId(userId);
    }

    public UserInfo getUserInfoByPhone(String phone) {
        return userInfoDao.findUserInfoByPhone(phone);
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

    public String updateAvatarFilenameByPhone(MultipartFile file, String phone) throws DaoException, IOException {
        UserInfo userInfo = userInfoDao.findUserInfoByPhone(phone);
        if (userInfo != null) {
            String filename = FileUtil.upload(file, AVATAR_PATH);
            userInfoDao.updateAvatarFilename(filename, phone);
            return filename;
        } else {
            throw new DaoException("用户不存在！");
        }
    }
}
