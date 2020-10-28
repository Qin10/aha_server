package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.*;
import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.config.JWTConfig;
import cn.hdustea.aha_server.entity.Contract;
import cn.hdustea.aha_server.entity.Resume;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.DaoException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.InvalidPasswordException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.UserNotFoundException;
import cn.hdustea.aha_server.exception.apiException.daoException.insertException.AccountExistedException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.util.FileUtil;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;

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
    private ResumeService resumeService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SmsService smsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //    private static final String REGISTER_MESSAGE_CODE_PREFIX = "user:register:code:";
    @Resource
    private JWTConfig jwtConfig;
    @Resource
    private FileUploadPathConfig fileUploadPathConfig;
    @Resource
    private ContractService contractService;

    public AuthService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * 传入手机号+密码完成登录校验并获取令牌
     *
     * @param loginUser 登录信息实体类
     * @return token令牌和用户部分信息
     * @throws Exception 向上抛出异常
     */
    public TokenAndPersonalUserInfoBean login(LoginUser loginUser) throws Exception {
        User user = userService.getUserByPhone(loginUser.getPhone());
        if (user != null) {
            if (bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
                String token = signToken(user);
                PersonalUserInfoBean personalUserInfo = userInfoService.getPersonalUserInfo(user.getPhone());
                return new TokenAndPersonalUserInfoBean(token, personalUserInfo);
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
     * @throws DaoException          数据库操作异常
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
        user.setSignedNotice(registerUser.isSignedNotice());
        user.setRoleId(1);
        user.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        if (userService.getUserByPhone(user.getPhone()) == null) {
            userService.saveUser(user);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId());
            userInfo.setNickname(registerUser.getNickname());
            Resume resume = new Resume();
            resumeService.saveResume(resume);
            userInfo.setResumeId(resume.getId().toString());
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
     * 处理用户修改密码的请求
     *
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
     * 处理用户同意服务协议的接口，将用户表相应字段置为true，并返回新的token
     *
     * @param phone 手机号
     * @return 新的token令牌
     * @throws AccountNotFoundException 用户不存在异常
     */
    public String signNotice(String phone) throws AccountNotFoundException {
        User user = userService.getUserByPhone(phone);
        if (user == null) {
            throw new AccountNotFoundException();
        }
        userService.updatesignedNotice(phone, true);
        user.setSignedNotice(true);
        return signToken(user);
    }

    public void signContract(String phone,MultipartFile file, Contract contract) throws IOException, AccountNotFoundException {
        User user = userService.getUserByPhone(phone);
        if (user == null) {
            throw new AccountNotFoundException();
        }
        contract.setUserId(user.getId());
        String filename = FileUtil.upload(file, fileUploadPathConfig.getContractSignaturePath());
        contract.setSignatureFilename(filename);
        contract.setSignTime(new Date());
        contractService.saveContract(contract);
        userService.updateSignedContract(phone,true);
    }

    /**
     * 处理用户登出请求，移除token
     *
     * @param phone 手机号
     */
    public void logout(String phone) {
        redisUtil.del(phone);
    }

    private String signToken(User user) {
        JwtPayloadBean jwtPayloadBean = JWTUtil.packagePayload(user);
        String token = JWTUtil.sign(jwtPayloadBean, jwtConfig.getSecret(), jwtConfig.getExpireTime());
        redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), token, jwtConfig.getRefreshTokenExpireTime());
        return token;
    }
}
