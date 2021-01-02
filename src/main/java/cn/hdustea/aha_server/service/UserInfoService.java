package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.UserInfoMapper;
import cn.hdustea.aha_server.vo.OauthVo;
import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import cn.hdustea.aha_server.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private OauthService oauthService;

    /**
     * 根据用户id获取用户公有信息
     *
     * @param userId 用户id
     * @return 用户公有信息实体类
     */
    public UserInfo getUserInfoByUserId(Integer userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据用户id获取用户公开信息VO
     *
     * @param userId 用户id
     * @return 用户公开信息VO
     */
    public UserRoughInfoVo getUserInfoVoByUserId(Integer userId) {
        return userInfoMapper.selectRoughVoByPrimaryKey(userId);
    }

    /**
     * 根据用户id获取用户个人详细信息
     *
     * @param userId 用户id
     * @return 用户个人详细信息
     */
    public PersonalUserInfoVo getPersonalUserInfo(Integer userId) {
        UserVo userVo = userService.getUserVoById(userId);
        List<OauthVo> oauthVos = oauthService.getAllOauthVoByUserId(userId);
        UserInfo userInfo = getUserInfoByUserId(userId);
        PersonalUserInfoVo personalUserInfoVo = new PersonalUserInfoVo();
        personalUserInfoVo.setAhaCredit(userVo.getAhaCredit());
        personalUserInfoVo.setAhaPoint(userVo.getAhaPoint());
        personalUserInfoVo.setSignedNotice(userVo.getSignedNotice());
        personalUserInfoVo.setSignedContract(userVo.getSignedContract());
        personalUserInfoVo.setRole(userVo.getRole());
        personalUserInfoVo.setOauths(oauthVos);
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
     * 根据用户id修改用户公有信息
     *
     * @param userInfo 用户公有信息实体类
     * @param userId   用户id
     */
    public void updateUserInfoByUserId(UserInfo userInfo, Integer userId) {
        userInfo.setUserId(userId);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 根据用户id更新用户公开信息
     *
     * @param userInfo 用户公开信息
     * @param userId   用户id
     * @throws SelectException 用户未找到异常
     */
    public void updateUserInfoByUserId(UserInfo userInfo, int userId) throws SelectException {
        UserVo userVo = userService.getExistUserVoById(userId);
        updateUserInfoByUserId(userInfo, userVo.getId());
    }
}
