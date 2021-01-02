package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.config.JwtConfig;
import cn.hdustea.aha_server.config.WechatConfig;
import cn.hdustea.aha_server.constants.OauthTypes;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.constants.SmsConstants;
import cn.hdustea.aha_server.dto.*;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.AuthorizationException;
import cn.hdustea.aha_server.exception.apiException.DaoException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.AccountNotFoundException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.InvalidPasswordException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.exception.apiException.daoException.insertException.AccountExistedException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.util.*;
import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import cn.hdustea.aha_server.vo.TokenAndPersonalUserInfoVo;
import cn.hdustea.aha_server.vo.UserVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

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
    private OauthService oauthService;
    @Resource
    private RedisService redisService;
    @Resource
    private SmsService smsService;
    @Resource
    private JwtConfig jwtConfig;
    @Resource
    private FileUploadPathConfig fileUploadPathConfig;
    @Resource
    private WechatConfig wechatConfig;
    @Resource
    private ContractService contractService;
    @Resource
    private HttpServletRequest request;

    /**
     * 传入手机号+密码完成登录校验并获取令牌
     *
     * @param phoneLoginUserDto 登录信息实体类
     * @return token令牌和用户部分信息
     * @throws Exception 向上抛出异常
     */
    public TokenAndPersonalUserInfoVo loginByPhone(PhoneLoginUserDto phoneLoginUserDto) throws Exception {
        Oauth oauth = oauthService.getOauthByOauthTypeAndOauthId(OauthTypes.PHONE, phoneLoginUserDto.getPhone());
        if (oauth == null) {
            throw new AccountNotFoundException("用户不存在！");
        }

        UserVo userVo = userService.getUserVoById(oauth.getUserId());
        if (EncryptUtil.getSha256(phoneLoginUserDto.getPassword()).equals(userVo.getPassword())) {
            return excuteLoginByUserId(userVo.getId());
        } else {
            throw new InvalidPasswordException("用户名或密码错误！");
        }
    }

    /**
     * 处理绑定手机号请求
     *
     * @param phoneBindDto 绑定手机号信息
     * @param userId       用户id
     * @throws AuthorizationException 授权异常
     * @throws MessageCheckException  短信校验异常
     */
    public void bindPhone(PhoneBindDto phoneBindDto, Integer userId) throws AuthorizationException, MessageCheckException {
        boolean SmsVerifyResult = smsService.verifySmsCode(phoneBindDto.getPhone(), phoneBindDto.getCode(), SmsConstants.BIND_PHONE_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        if (oauthService.getOauthByOauthTypeAndUserId(OauthTypes.PHONE, userId) != null) {
            throw new AuthorizationException("您已绑定过手机号！");
        }
        if (oauthService.getOauthByOauthTypeAndOauthId(OauthTypes.PHONE, phoneBindDto.getPhone()) != null) {
            throw new AuthorizationException("该手机号已被绑定过！");
        }
        Oauth oauth = new Oauth();
        oauth.setUserId(userId);
        oauth.setOauthType(OauthTypes.PHONE);
        oauth.setOauthId(phoneBindDto.getPhone());
        oauthService.saveOauth(oauth);
    }

    /**
     * 传入注册信息和短信验证码，完成验证码校验并处理注册请求
     *
     * @param phoneRegisterUserDto 包含注册信息的实体
     * @return token令牌和用户部分信息
     * @throws DaoException          数据库操作异常
     * @throws MessageCheckException 短信验证码校验异常
     */
    @Transactional(rollbackFor = Exception.class)
    public TokenAndPersonalUserInfoVo registerByPhone(PhoneRegisterUserDto phoneRegisterUserDto) throws DaoException, MessageCheckException {
        boolean SmsVerifyResult = smsService.verifySmsCode(phoneRegisterUserDto.getPhone(), phoneRegisterUserDto.getCode(), SmsConstants.REGISTER_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        Oauth possibleOauth = oauthService.getOauthByOauthTypeAndOauthId(OauthTypes.PHONE, phoneRegisterUserDto.getPhone());
        if (possibleOauth != null) {
            throw new AccountExistedException();
        }
        User user = new User();
        user.setPassword(EncryptUtil.getSha256((phoneRegisterUserDto.getPassword())));
        user.setSignedNotice(phoneRegisterUserDto.isSignedNotice());
        user.setRoleId(1);
        user.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        userService.saveUser(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNickname(phoneRegisterUserDto.getNickname());
        userInfoService.saveUserInfo(userInfo);
        Oauth oauth = new Oauth();
        oauth.setUserId(user.getId());
        oauth.setOauthType(OauthTypes.PHONE);
        oauth.setOauthId(phoneRegisterUserDto.getPhone());
        oauthService.saveOauth(oauth);
        Resume resume = new Resume();
        resumeService.updateResumeByUserId(resume, user.getId());
        return excuteLoginByUserId(user.getId());
    }

    /**
     * 处理用户修改密码的请求
     *
     * @param phoneChangePasswordDto 存放修改密码相关信息的实体类
     * @param phone                  手机号
     * @throws MessageCheckException    短信验证码校验异常
     * @throws AccountNotFoundException 用户不存在异常
     */
    public void changePasswordByPhone(PhoneChangePasswordDto phoneChangePasswordDto, String phone) throws MessageCheckException, AccountNotFoundException {
        boolean SmsVerifyResult = smsService.verifySmsCode(phone, phoneChangePasswordDto.getCode(), SmsConstants.CHANGE_PASSWORD_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        Oauth oauth = oauthService.getOauthByOauthTypeAndOauthId(OauthTypes.PHONE, phone);
        if (oauth == null) {
            throw new AccountNotFoundException("用户不存在！");
        }
        String encodedPassword = EncryptUtil.getSha256(phoneChangePasswordDto.getNewPassword());
        userService.updatePassword(oauth.getUserId(), encodedPassword);
    }

    /**
     * 处理用户同意服务协议的接口，将用户表相应字段置为true，并返回新的token
     *
     * @param userId 用户id
     * @return 新的token令牌
     * @throws UpdateException 更新异常
     */
    public String signNotice(Integer userId) throws UpdateException {
        UserVo userVo = userService.getUserVoById(userId);
        if (userVo.getSignedNotice()) {
            throw new UpdateException("已经签署过服务协议！");
        }
        userService.updatesignedNotice(userVo.getId(), true);
        userVo.setSignedNotice(true);
        return signToken(userVo);
    }

    /**
     * 处理用户签署合同的接口，合同表内新增字段，并将用户表相应字段置为true，并返回新的token
     *
     * @param userId   用户id
     * @param file     合同签字文件
     * @param contract 合同信息
     * @return 新的token令牌
     * @throws IOException     文件IO异常
     * @throws UpdateException 更新异常
     */
    @Transactional(rollbackFor = Exception.class)
    public String signContract(Integer userId, MultipartFile file, Contract contract) throws IOException, UpdateException, InsertException {
        UserVo userVo = userService.getUserVoById(userId);
        if (userVo.getSignedContract()) {
            throw new UpdateException("已经签署过合同！");
        }
        contract.setUserId(userVo.getId());
        String filename = FileUtil.upload(file, fileUploadPathConfig.getContractSignaturePath());
        contract.setSignatureFilename(filename);
        contract.setSignTime(new Date());
        contractService.saveContract(contract);
        userService.updateSignedContract(userVo.getId(), true);
        userVo.setSignedContract(true);
        return signToken(userVo);
    }

    /**
     * 处理用户登出请求，移除token
     *
     * @param userId 用户id
     */
    public void logout(Integer userId) {
        redisService.del(userId.toString());
    }

    /**
     * 处理微信登录或注册请求
     *
     * @param wechatRegisterUserDto 微信注册信息
     * @return token和部分用户信息
     * @throws JsonProcessingException jackson异常
     * @throws SelectException         查询异常
     * @throws AuthorizationException  授权异常
     */
    public TokenAndPersonalUserInfoVo LoginByWechat(WechatRegisterUserDto wechatRegisterUserDto) throws JsonProcessingException, SelectException, AuthorizationException {
        String openid = WechatUtil.getWxInfo(wechatRegisterUserDto.getCode(), wechatConfig.getAppid(), wechatConfig.getSecret()).getOpenid();
        if (openid == null) {
            throw new AuthorizationException("非法授权码！");
        }
        Oauth wechatOauth = oauthService.getOauthByOauthTypeAndOauthId(OauthTypes.WECHAT, openid);
        if (wechatOauth != null) {
            return excuteLoginByUserId(wechatOauth.getUserId());
        } else {
            User user = new User();
            user.setSignedNotice(wechatRegisterUserDto.isSignedNotice());
            user.setRoleId(1);
            user.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            userService.saveUser(user);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId());
            userInfo.setNickname(wechatRegisterUserDto.getNickname());
            userInfo.setAvatarUrl(wechatRegisterUserDto.getAvatarUrl());
            userInfoService.saveUserInfo(userInfo);
            Oauth oauth = new Oauth();
            oauth.setUserId(user.getId());
            oauth.setOauthType(OauthTypes.WECHAT);
            oauth.setOauthId(openid);
            oauthService.saveOauth(oauth);
            Resume resume = new Resume();
            resumeService.updateResumeByUserId(resume, user.getId());
            return excuteLoginByUserId(user.getId());
        }
    }

    /**
     * 处理绑定微信请求
     *
     * @param userId 用户id
     * @param code   微信code
     * @throws Exception 抛出异常
     */
    public void bindWechat(int userId, String code) throws Exception {
        String openid = WechatUtil.getWxInfo(code, wechatConfig.getAppid(), wechatConfig.getSecret()).getOpenid();
        if (openid == null) {
            throw new AuthorizationException("非法授权码！");
        }
        if (oauthService.getOauthByOauthTypeAndUserId(OauthTypes.WECHAT, userId) != null) {
            throw new AuthorizationException("您已绑定过微信账号！");
        }
        if (oauthService.getOauthByOauthTypeAndOauthId(OauthTypes.WECHAT, openid) != null) {
            throw new AuthorizationException("该微信账号已被绑定过！");
        }
        Oauth oauth = new Oauth();
        oauth.setUserId(userId);
        oauth.setOauthType(OauthTypes.WECHAT);
        oauth.setOauthId(openid);
        oauthService.saveOauth(oauth);
    }

    /**
     * 签署token令牌
     *
     * @param userVo 用户私有信息封装
     * @return token令牌
     */
    private String signToken(UserVo userVo) {
        JwtPayloadDto jwtPayloadDto = JwtUtil.packagePayload(userVo);

        String token = JwtUtil.sign(jwtPayloadDto, jwtConfig.getSecret(), jwtConfig.getExpireTime());
        redisService.set(RedisConstants.REFRESH_TOKEN_PREFIX + userVo.getId(), token, jwtConfig.getRefreshTokenExpireTime());
        return token;
    }

    /**
     * 处理登录过程
     *
     * @param userId 用户id
     * @return token和部分用户信息
     * @throws SelectException 用户未找到异常
     */
    private TokenAndPersonalUserInfoVo excuteLoginByUserId(int userId) throws SelectException {
        UserVo userVo = userService.getExistUserVoById(userId);
        messageService.saveAllNoticeNotReadByReceiverUserId(userVo.getId());
        String token = signToken(userVo);

        PersonalUserInfoVo personalUserInfo = userInfoService.getPersonalUserInfo(userVo.getId());
        MDC.put("userId", userVo.getId().toString());
        MDC.put("ip", IpUtil.getIpAddr(request));
        return new TokenAndPersonalUserInfoVo(token, personalUserInfo);
    }
}
