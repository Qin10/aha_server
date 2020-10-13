package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.LoginUser;
import cn.hdustea.aha_server.bean.RegisterUser;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.authenticationException.InvalidPasswordException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.UserNotFoundException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.insertException.AccountExistedException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 授权鉴权服务类
 *
 * @author STEA_YY
 **/
@Service
public class AuthService {
    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SmsService smsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final int REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60;
    private static final String REFRESH_TOKEN_PREFIX = "user:token:";
    private static final String REGISTER_MESSAGE_CODE_PREFIX = "user:register:code:";

    public AuthService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * 传入手机号+密码完成登录校验并获取令牌
     *
     * @param loginUser
     * @return token令牌
     * @throws Exception
     */
    public String login(LoginUser loginUser) throws Exception {
        User user = userService.getUserByPhone(loginUser.getPhone());
        if (user != null) {
            if (bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
                String token = JWTUtil.sign(user.getPhone(), user.getPassword());
                redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), token, REFRESH_TOKEN_EXPIRE_TIME);
                return token;
            } else {
                throw new InvalidPasswordException("用户名或密码错误！");
            }
        } else {
            throw new UserNotFoundException("用户不存在！");
        }
    }

    /**
     * 传入注册信息和短信验证码，完成验证码校验并处理注册请求
     *
     * @param registerUser
     * @throws Exception
     */
    public void register(RegisterUser registerUser) throws Exception {
        boolean SmsVerifyResult = smsService.verifySmsCode(registerUser.getPhone(), registerUser.getCode());
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
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
            throw new AccountExistedException();
        }
    }

    /**
     * 处理用户登出请求，移除token
     *
     * @param phone 手机号
     */
    public void logout(String phone) {
        redisUtil.del(phone);
    }
}
