package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.LoginUser;
import cn.hdustea.aha_server.bean.RegisterUser;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.DaoException;
import cn.hdustea.aha_server.exception.InvalidPasswordException;
import cn.hdustea.aha_server.exception.MessageVerificationException;
import cn.hdustea.aha_server.exception.UserNotFoundException;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @program: aha_server
 * @description: 授权鉴权服务类
 * @author: HduStea_YY
 * @create: 2020-10-10 02:07
 **/
@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SmsService smsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final int REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60;
    private static final String REFRESH_TOKEN_PREFIX = "user:token:";
    private static final String REGISTER_MESSAGE_CODE_PREFIX = "user:register:code:";

    public AuthService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String login(LoginUser loginUser) throws Exception {
        User user = userService.getUserByPhone(loginUser.getAccount());
        if (user != null) {
            if (bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
                String token = JWTUtil.sign(user.getPhone(), user.getPassword());
                redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), token, REFRESH_TOKEN_EXPIRE_TIME);
                return token;
            } else {
                throw new InvalidPasswordException("用户名或密码错误！");
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    public void register(RegisterUser registerUser) throws Exception {
        boolean SmsVerifyResult = smsService.verifySmsCode(registerUser.getPhone(), registerUser.getCode());
        if (!SmsVerifyResult) {
            throw new MessageVerificationException("验证码失效或错误！");
        }
        User user = new User();
        user.setPhone(registerUser.getPhone());
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setRoleId(1);
        user.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        if (userService.getUserByPhone(user.getPhone()) == null) {
            userService.saveUser(user);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId());
            userInfo.setNickname(registerUser.getNickname());
            UserInfo possibleUserInfo = userInfoService.getUserInfoByUserId(user.getId());
            if (possibleUserInfo != null) {
                userInfoService.deleteUserInfoById(possibleUserInfo.getId());
            }
            userInfoService.saveUserInfo(userInfo);
        } else {
            throw new DaoException("账号已存在！");
        }
    }

    public void logout(String phone) {
        redisUtil.del(phone);
    }
}
