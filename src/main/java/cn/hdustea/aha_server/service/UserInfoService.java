package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.UserInfoMapper;
import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
     * 根据手机号获取用户公有信息
     *
     * @param phone 用户手机号
     * @return 用户公有信息实体类
     */
    public UserInfo getUserInfoByPhone(String phone) {
        userService.getUserByPhone(phone);
        return userInfoMapper.selectByPrimaryKey(phone);
    }

    /**
     * 根据手机号获取用户个人详细信息
     *
     * @param phone 用户手机号
     * @return 用户个人详细信息
     */
    public PersonalUserInfoVo getPersonalUserInfo(String phone) {
        User user = userService.getUserByPhone(phone);
        UserInfo userInfo = getUserInfoByPhone(phone);
        PersonalUserInfoVo personalUserInfoVo = new PersonalUserInfoVo();
        personalUserInfoVo.setContribPoint(user.getContribPoint());
        personalUserInfoVo.setSignedNotice(user.getSignedNotice());
        personalUserInfoVo.setSignedContract(user.getSignedContract());
        personalUserInfoVo.setRole(user.getRole());
        personalUserInfoVo.setUserInfo(userInfo);
        return personalUserInfoVo;
    }

    /**
     * 保存用户公有信息
     *
     * @param userInfo 用户公有信息实体类
     */
    public void saveUserInfo(UserInfo userInfo) {
        userInfoMapper.insertSelective(userInfo);
    }

    /**
     * 根据手机号修改用户公有信息
     *
     * @param userInfo 用户公有信息实体类
     * @param phone    用户手机号
     */
    public void updateUserInfoByPhone(UserInfo userInfo, String phone) {
        userInfo.setUserPhone(phone);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public void updateUserInfoByUserId(UserInfo userInfo, int userId) throws SelectException {
        User user = userService.getExistUserById(userId);
        updateUserInfoByPhone(userInfo, user.getPhone());
    }
}
