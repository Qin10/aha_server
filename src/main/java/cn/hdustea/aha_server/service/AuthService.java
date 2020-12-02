package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.FileUploadPathConfig;
import cn.hdustea.aha_server.config.JWTConfig;
import cn.hdustea.aha_server.dto.*;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.enums.OauthType;
import cn.hdustea.aha_server.exception.apiException.AuthorizationException;
import cn.hdustea.aha_server.exception.apiException.DaoException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.InvalidPasswordException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.AccountNotFoundException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.WechatUnauthorizedException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.exception.apiException.daoException.insertException.AccountExistedException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.util.*;
import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import cn.hdustea.aha_server.vo.TokenAndPersonalUserInfoVo;
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
    private OauthService oauthService;
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
     * @param phoneLoginUserDto 登录信息实体类
     * @return token令牌和用户部分信息
     * @throws Exception 向上抛出异常
     */
    public TokenAndPersonalUserInfoVo loginByPhone(PhoneLoginUserDto phoneLoginUserDto) throws Exception {
        Oauth oauth = oauthService.getOauthByOauthTypeAndOauthId(OauthType.PHONE.getValue(), phoneLoginUserDto.getPhone());
        if (oauth == null) {
            throw new AccountNotFoundException("用户不存在！");
        }

        User user = userService.getUserById(oauth.getUserId());
        if (EncryptUtil.getSHA256(phoneLoginUserDto.getPassword()).equals(user.getPassword())) {
            return excuteLoginByUserId(user.getId());
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
        boolean SmsVerifyResult = smsService.verifySmsCode(phoneBindDto.getPhone(), phoneBindDto.getCode(), SmsService.BIND_PHONE_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        if (oauthService.getOauthByOauthTypeAndUserId(OauthType.PHONE.getValue(), userId) != null) {
            throw new AuthorizationException("您已绑定过手机号！");
        }
        if (oauthService.getOauthByOauthTypeAndOauthId(OauthType.PHONE.getValue(), phoneBindDto.getPhone()) != null) {
            throw new AuthorizationException("该手机号已被绑定过！");
        }
        Oauth oauth = new Oauth();
        oauth.setUserId(userId);
        oauth.setOauthType(OauthType.PHONE.getValue());
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
        boolean SmsVerifyResult = smsService.verifySmsCode(phoneRegisterUserDto.getPhone(), phoneRegisterUserDto.getCode(), SmsService.REGISTER_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        Oauth possibleOauth = oauthService.getOauthByOauthTypeAndOauthId(OauthType.PHONE.getValue(), phoneRegisterUserDto.getPhone());
        if (possibleOauth != null) {
            throw new AccountExistedException();
        }
        User user = new User();
        user.setPassword(EncryptUtil.getSHA256((phoneRegisterUserDto.getPassword())));
        user.setSignedNotice(phoneRegisterUserDto.isSignedNotice());
        user.setRoleId(1);
        user.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        userService.saveUser(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNickname(phoneRegisterUserDto.getNickname());
        userInfo.setAvatarUrl("https://aha-public.oss-cn-hangzhou.aliyuncs.com/AhaIcon/logo.png");
        userInfoService.saveUserInfo(userInfo);
        Oauth oauth = new Oauth();
        oauth.setUserId(user.getId());
        oauth.setOauthType(OauthType.PHONE.getValue());
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
        boolean SmsVerifyResult = smsService.verifySmsCode(phone, phoneChangePasswordDto.getCode(), SmsService.CHANGE_PASSWORD_MESSAGE);
        if (!SmsVerifyResult) {
            throw new MessageCheckException();
        }
        Oauth oauth = oauthService.getOauthByOauthTypeAndOauthId(OauthType.PHONE.getValue(), phone);
        if (oauth == null) {
            throw new AccountNotFoundException("用户不存在！");
        }
        String encodedPassword = EncryptUtil.getSHA256(phoneChangePasswordDto.getNewPassword());
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
        User user = userService.getUserById(userId);
        if (user.getSignedNotice()) {
            throw new UpdateException("已经签署过服务协议！");
        }
        userService.updatesignedNotice(user.getId(), true);
        user.setSignedNotice(true);
        return signToken(user);
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
        User user = userService.getUserById(userId);
        if (user.getSignedContract()) {
            throw new UpdateException("已经签署过合同！");
        }
        contract.setUserId(user.getId());
        String filename = FileUtil.upload(file, fileUploadPathConfig.getContractSignaturePath());
        contract.setSignatureFilename(filename);
        contract.setSignTime(new Date());
        contractService.saveContract(contract);
        userService.updateSignedContract(user.getId(), true);
        user.setSignedContract(true);
        return signToken(user);
    }

    /**
     * 处理用户登出请求，移除token
     *
     * @param userId 用户id
     */
    public void logout(Integer userId) {
        redisUtil.del(userId.toString());
    }

    /**
     * 使用微信请求code完成登录校验
     *
     * @param code 微信请求code
     * @return token令牌
     * @throws WechatUnauthorizedException 微信小程序授权信息未找到异常
     */
    public TokenAndPersonalUserInfoVo LoginByWechat(String code) throws Exception {
        String openid = WechatUtil.getWxInfo(code).getOpenid();
        Oauth wechatOauth = oauthService.getOauthByOauthTypeAndOauthId(OauthType.WECHAT.getValue(), openid);
        if (wechatOauth == null) {
            throw new WechatUnauthorizedException();
        } else {
            return excuteLoginByUserId(wechatOauth.getUserId());
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
        String openid = WechatUtil.getWxInfo(code).getOpenid();
        if (openid == null) {
            throw new AuthorizationException("非法授权码！");
        }
        if (oauthService.getOauthByOauthTypeAndUserId(OauthType.WECHAT.getValue(), userId) != null) {
            throw new AuthorizationException("您已绑定过微信账号！");
        }
        if (oauthService.getOauthByOauthTypeAndOauthId(OauthType.WECHAT.getValue(), openid) != null) {
            throw new AuthorizationException("该微信账号已被绑定过！");
        }
        Oauth oauth = new Oauth();
        oauth.setUserId(userId);
        oauth.setOauthType(OauthType.WECHAT.getValue());
        oauth.setOauthId(openid);
        oauthService.saveOauth(oauth);
    }

    /**
     * 处理微信登录请求
     *
     * @param wechatRegisterUserDto 微信登录信息
     * @return token和部分用户信息
     * @throws Exception 抛出异常
     */
    @Transactional(rollbackFor = Exception.class)
    public TokenAndPersonalUserInfoVo registerByWechat(WechatRegisterUserDto wechatRegisterUserDto) throws Exception {
        String openid = WechatUtil.getWxInfo(wechatRegisterUserDto.getCode()).getOpenid();
        if (openid == null) {
            throw new AuthorizationException("非法授权码！");
        }
        Oauth possibleOauth = oauthService.getOauthByOauthTypeAndOauthId(OauthType.WECHAT.getValue(), openid);
        if (possibleOauth != null) {
            throw new AccountExistedException();
        }
        User user = new User();
        user.setSignedNotice(wechatRegisterUserDto.isSignedNotice());
        user.setRoleId(1);
        user.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        userService.saveUser(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNickname(wechatRegisterUserDto.getNickname());
        userInfo.setAvatarUrl("https://aha-public.oss-cn-hangzhou.aliyuncs.com/AhaIcon/logo.png");
        userInfoService.saveUserInfo(userInfo);
        Oauth oauth = new Oauth();
        oauth.setUserId(user.getId());
        oauth.setOauthType(OauthType.WECHAT.getValue());
        oauth.setOauthId(openid);
        oauthService.saveOauth(oauth);
        Resume resume = new Resume();
        resumeService.updateResumeByUserId(resume, user.getId());
        return excuteLoginByUserId(user.getId());
    }

    /**
     * 签署token令牌
     *
     * @param user 用户私有信息
     * @return token令牌
     */
    private String signToken(User user) {
        JwtPayloadDto jwtPayloadDto = JWTUtil.packagePayload(user);

        String token = JWTUtil.sign(jwtPayloadDto, jwtConfig.getSecret(), jwtConfig.getExpireTime());
        redisUtil.set(REFRESH_TOKEN_PREFIX + user.getId(), token, jwtConfig.getRefreshTokenExpireTime());
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
        User user = userService.getExistUserById(userId);
        messageService.saveAllNoticeNotReadByReceiverUserId(user.getId());
        String token = signToken(user);

        PersonalUserInfoVo personalUserInfo = userInfoService.getPersonalUserInfo(user.getId());
        MDC.put("id", user.getId().toString());
        MDC.put("ip", IpUtil.getIpAddr(request));
        return new TokenAndPersonalUserInfoVo(token, personalUserInfo);
    }
}
