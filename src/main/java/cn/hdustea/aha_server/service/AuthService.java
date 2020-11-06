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
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.exception.apiException.daoException.insertException.AccountExistedException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.util.FileUtil;
import cn.hdustea.aha_server.util.IpUtil;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import org.slf4j.MDC;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private HttpServletRequest request;

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
                MDC.put("phone", loginUser.getPhone());
                MDC.put("ip", IpUtil.getIpAddr(request));
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
    @Transactional(rollbackFor = Exception.class)
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
        try {
            userService.getUserByPhone(user.getPhone());
            throw new AccountExistedException();
        } catch (SelectException e) {
            userService.saveUser(user);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId());
            userInfo.setNickname(registerUser.getNickname());
            userInfoService.saveUserInfo(userInfo);
            Resume resume = new Resume();
            resumeService.updateResumeByPhone(resume, user.getPhone());
        }
    }

    /**
     * 处理用户修改密码的请求
     *
     * @param changePasswordBean 存放修改密码相关信息的实体类
     * @throws MessageCheckException    短信验证码校验异常
     * @throws AccountNotFoundException 账号未找到异常
     */
    public void changePassword(ChangePasswordBean changePasswordBean, String phone) throws MessageCheckException, SelectException {
        boolean SmsVerifyResult = smsService.verifySmsCode(phone, changePasswordBean.getCode(), SmsService.CHANGE_PASSWORD_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        User user = userService.getUserByPhone(phone);
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
    public String signNotice(String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        userService.updatesignedNotice(phone, true);
        user.setSignedNotice(true);
        return signToken(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public String signContract(String phone, MultipartFile file, Contract contract) throws IOException, UpdateException, SelectException {
        User user = userService.getUserByPhone(phone);
        if (user.getSignedContract()) {
            throw new UpdateException("已经签署过合同！");
        }
        contract.setUserId(user.getId());
        String filename = FileUtil.upload(file, fileUploadPathConfig.getContractSignaturePath());
        contract.setSignatureFilename(filename);
        contract.setSignTime(new Date());
        contractService.saveContract(contract);
        userService.updateSignedContract(phone, true);
        user.setSignedContract(true);
        return signToken(user);
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
