package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.config.JWTConfig;
import cn.hdustea.aha_server.dto.JwtPayloadDto;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.util.*;
import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import cn.hdustea.aha_server.vo.TokenAndPersonalUserInfoVo;
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
import cn.hdustea.aha_server.dto.ChangePasswordDto;
import cn.hdustea.aha_server.dto.LoginUserDto;
import cn.hdustea.aha_server.dto.RegisterUserDto;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

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
    private MessageService messageService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SmsService smsService;
    @Resource
    private JWTConfig jwtConfig;
    @Resource
    private FileUploadPathConfig fileUploadPathConfig;
    @Resource
    private ContractService contractService;
    @Resource
    private HttpServletRequest request;

    /**
     * 传入手机号+密码完成登录校验并获取令牌
     *
     * @param loginUserDto 登录信息实体类
     * @return token令牌和用户部分信息
     * @throws Exception 向上抛出异常
     */
    public TokenAndPersonalUserInfoVo login(LoginUserDto loginUserDto) throws Exception {
        User user = userService.getExistUserByPhone(loginUserDto.getPhone());
        if (user != null) {
            if (EncryptUtil.getSHA256(loginUserDto.getPassword()).equals(user.getPassword())) {
                messageService.saveAllNoticeNotReadByReceiverPhone(loginUserDto.getPhone());
                String token = signToken(user);
                PersonalUserInfoVo personalUserInfo = userInfoService.getPersonalUserInfo(user.getPhone());
                MDC.put("phone", loginUserDto.getPhone());
                MDC.put("ip", IpUtil.getIpAddr(request));
                return new TokenAndPersonalUserInfoVo(token, personalUserInfo);
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
     * @param registerUserDto 包含注册信息的实体
     * @throws DaoException          数据库操作异常
     * @throws MessageCheckException 短信验证码校验异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterUserDto registerUserDto) throws DaoException, MessageCheckException {
        boolean SmsVerifyResult = smsService.verifySmsCode(registerUserDto.getPhone(), registerUserDto.getCode(), SmsService.REGISTER_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        User possibleUser = userService.getUserByPhone(registerUserDto.getPhone());
        if (possibleUser != null) {
            throw new AccountExistedException();
        }
        User user = new User();
        user.setPhone(registerUserDto.getPhone());
        user.setPassword(EncryptUtil.getSHA256((registerUserDto.getPassword())));
        user.setSignedNotice(registerUserDto.isSignedNotice());
        user.setRoleId(1);
        user.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        userService.saveUser(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserPhone(registerUserDto.getPhone());
        userInfo.setNickname(registerUserDto.getNickname());
        userInfo.setAvatarUrl("https://aha-public.oss-cn-hangzhou.aliyuncs.com/AhaIcon/logo.png");
        userInfoService.saveUserInfo(userInfo);
        Resume resume = new Resume();
        resumeService.updateResumeByPhone(resume, user.getPhone());
    }

    /**
     * 处理用户修改密码的请求
     *
     * @param changePasswordDto 存放修改密码相关信息的实体类
     * @throws MessageCheckException 短信验证码校验异常
     * @throws SelectException       用户不存在异常
     */
    public void changePassword(ChangePasswordDto changePasswordDto, String phone) throws MessageCheckException, SelectException {
        boolean SmsVerifyResult = smsService.verifySmsCode(phone, changePasswordDto.getCode(), SmsService.CHANGE_PASSWORD_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        userService.getExistUserByPhone(phone);
        String encodedPassword = EncryptUtil.getSHA256(changePasswordDto.getNewPassword());
        userService.updatePassword(phone, encodedPassword);
    }

    /**
     * 处理用户同意服务协议的接口，将用户表相应字段置为true，并返回新的token
     *
     * @param phone 手机号
     * @return 新的token令牌
     * @throws SelectException 用户不存在异常
     */
    public String signNotice(String phone) throws SelectException, UpdateException {
        User user = userService.getExistUserByPhone(phone);
        if (user.getSignedNotice()) {
            throw new UpdateException("已经签署过服务协议！");
        }
        userService.updatesignedNotice(phone, true);
        user.setSignedNotice(true);
        return signToken(user);
    }

    /**
     * 处理用户签署合同的接口，合同表内新增字段，并将用户表相应字段置为true，并返回新的token
     *
     * @param phone    手机号
     * @param file     合同签字文件
     * @param contract 合同信息
     * @return 新的token令牌
     * @throws IOException     文件IO异常
     * @throws UpdateException 更新异常
     * @throws SelectException 用户不存在异常
     */
    @Transactional(rollbackFor = Exception.class)
    public String signContract(String phone, MultipartFile file, Contract contract) throws IOException, UpdateException, SelectException, InsertException {
        User user = userService.getExistUserByPhone(phone);
        if (user.getSignedContract()) {
            throw new UpdateException("已经签署过合同！");
        }
        contract.setUserPhone(phone);
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

    /**
     * 签署token令牌
     *
     * @param user 用户私有信息
     * @return token令牌
     */
    public String signToken(User user) {
        JwtPayloadDto jwtPayloadDto = JWTUtil.packagePayload(user);
        String token = JWTUtil.sign(jwtPayloadDto, jwtConfig.getSecret(), jwtConfig.getExpireTime());
        redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), token, jwtConfig.getRefreshTokenExpireTime());
        return token;
    }
}
