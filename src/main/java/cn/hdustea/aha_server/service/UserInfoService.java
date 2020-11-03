package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.PersonalUserInfoBean;
import cn.hdustea.aha_server.entity.UserCollection;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.UserCollectionMapper;
import cn.hdustea.aha_server.mapper.UserInfoMapper;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.DaoException;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 用户公有信息服务类
 *
 * @author STEA_YY
 **/
@Service
public class UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserService userService;

    /**
     * 根据用户id获取用户公有信息
     *
     * @param userId 用户id
     * @return 用户公有信息实体类
     */
    public UserInfo getUserInfoByUserId(int userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据手机号获取用户公有信息
     *
     * @param phone 用户手机号
     * @return 用户公有信息实体类
     */
    public UserInfo getUserInfoByPhone(String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        return userInfoMapper.selectByUserId(user.getId());
    }

    /**
     * 根据手机号获取用户个人详细信息
     *
     * @param phone 用户手机号
     * @return 用户个人详细信息
     */
    public PersonalUserInfoBean getPersonalUserInfo(String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        UserInfo userInfo = userInfoMapper.selectByUserId(user.getId());
        PersonalUserInfoBean personalUserInfoBean = new PersonalUserInfoBean();
        personalUserInfoBean.setPhone(user.getPhone());
        personalUserInfoBean.setContribPoint(user.getContribPoint());
        personalUserInfoBean.setSignedNotice(user.getSignedNotice());
        personalUserInfoBean.setSignedContract(user.getSignedContract());
        personalUserInfoBean.setUserInfo(userInfo);
        return personalUserInfoBean;
    }

    /**
     * 保存用户公有信息
     *
     * @param userInfo 用户公有信息实体类
     */
    public void saveUserInfo(UserInfo userInfo) {
        userInfoMapper.insertSelective(userInfo);
    }

    public void updateUserInfo(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 根据手机号修改用户公有信息
     *
     * @param userInfo 用户公有信息实体类
     * @param phone    用户手机号
     * @throws SelectException 用户不存在
     */
    public void updateUserInfoByPhone(UserInfo userInfo, String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        userInfoMapper.updateByUserId(userInfo, user.getId());
    }

    /**
     * 根据id删除用户公有信息
     *
     * @param id 用户公有信息id
     * @throws DeleteException 删除失败异常
     */
    public void deleteUserInfoById(int id) throws DaoException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        if (userInfo != null) {
            userInfoMapper.deleteByPrimaryKey(id);
        } else {
            throw new DeleteException("不存在对应记录，删除用户详情失败！");
        }
    }

    /**
     * 根据手机号更新用户头像文件名
     *
     * @param fileUrl 图片路径
     * @param phone   手机号
     * @throws SelectException 用户不存在
     */
    public void updateAvatarUrlByPhone(String fileUrl, String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        userInfoMapper.updateAvatarUrlByUserId(fileUrl, user.getId());
    }
}
