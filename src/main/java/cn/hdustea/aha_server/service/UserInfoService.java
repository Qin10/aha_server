package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.dao.UserInfoDao;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.DaoException;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.util.FileUtil;
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
    @Resource
    private FileUploadPathConfig fileUploadPathConfig;

    public UserInfo getUserInfoByUserId(int userId) {
        return userInfoDao.findUserInfoByUserId(userId);
    }

    public UserInfo getUserInfoByPhone(String phone) {
        return userInfoDao.findUserInfoByPhone(phone);
    }

    /**
     * 保存用户详细信息
     *
     * @param userInfo 用户详细信息实体类
     * @throws InsertException 插入失败实体类
     */
    public void saveUserInfo(UserInfo userInfo) throws InsertException {
        UserInfo possibleUserInfo = userInfoDao.findUserInfoById(userInfo.getUserId());
        if (possibleUserInfo == null) {
            userInfoDao.saveUserInfo(userInfo);
        } else {
            throw new InsertException("用户详情已存在，无法插入！");
        }
    }

    /**
     * 根据id删除用户详细信息
     *
     * @param id 用户id
     * @throws DeleteException 删除失败异常
     */
    public void deleteUserInfoById(int id) throws DaoException {
        UserInfo userInfo = userInfoDao.findUserInfoById(id);
        if (userInfo != null) {
            userInfoDao.deleteUserInfo(id);
        } else {
            throw new DeleteException("不存在对应记录，删除用户详情失败！");
        }
    }

    /**
     * 根据手机号更新用户头像
     *
     * @param file  图片文件
     * @param phone 手机号
     * @return 保存的图片文件名
     * @throws UpdateException 更新失败异常
     */
    public String updateAvatarFilenameByPhone(MultipartFile file, String phone) throws UpdateException {
        UserInfo userInfo = userInfoDao.findUserInfoByPhone(phone);
        if (userInfo != null) {
            String filename;
            try {
                filename = FileUtil.upload(file, fileUploadPathConfig.getAvatarPath());
            } catch (IOException e) {
                throw new UpdateException("未知错误，修改失败！");
            }
            userInfoDao.updateAvatarFilename(filename, phone);
            return filename;
        } else {
            throw new UpdateException("用户不存在，修改失败！");
        }
    }
}
