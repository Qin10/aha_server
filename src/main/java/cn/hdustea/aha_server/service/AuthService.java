package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.ChangePasswordBean;
import cn.hdustea.aha_server.bean.LoginUser;
import cn.hdustea.aha_server.bean.RegisterUser;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.DaoException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.InvalidPasswordException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.UserNotFoundException;
import cn.hdustea.aha_server.exception.apiException.daoException.insertException.AccountExistedException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.login.AccountNotFoundException;
import java.sql.Timestamp;

import static cn.hdustea.aha_server.util.RedisUtil.REFRESH_TOKEN_EXPIRE_TIME;
import static cn.hdustea.aha_server.util.RedisUtil.REFRESH_TOKEN_PREFIX;

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
//    private static final String REGISTER_MESSAGE_CODE_PREFIX = "user:register:code:";

    public AuthService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * 传入手机号+密码完成登录校验并获取令牌
     *
     * @param loginUser 登录信息实体类
     * @return token令牌
     * @throws Exception 向上抛出异常
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
     * @param registerUser 包含注册信息的实体
     * @throws DaoException 数据库操作异常
     * @throws MessageCheckException 短信验证码校验异常
     */
    public void register(RegisterUser registerUser) throws DaoException, MessageCheckException {
        boolean SmsVerifyResult = smsService.verifySmsCode(registerUser.getPhone(), registerUser.getCode(), SmsService.REGISTER_MESSAGE);
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
     * @param changePasswordBean 存放修改密码相关信息的实体类
     * @throws MessageCheckException    短信验证码校验异常
     * @throws AccountNotFoundException 账号未找到异常
     */
    public void changePassword(ChangePasswordBean changePasswordBean, String phone) throws MessageCheckException, AccountNotFoundException {
        boolean SmsVerifyResult = smsService.verifySmsCode(phone, changePasswordBean.getCode(), SmsService.CHANGE_PASSWORD_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        User user = userService.getUserByPhone(phone);
        if (user == null) {
            throw new AccountNotFoundException();
        }
        String encodedPassword = bCryptPasswordEncoder.encode(changePasswordBean.getNewPassword());
        userService.updatePassword(phone, encodedPassword);
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
