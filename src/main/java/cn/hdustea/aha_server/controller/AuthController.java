package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.dto.*;
import cn.hdustea.aha_server.entity.Contract;
import cn.hdustea.aha_server.exception.apiException.AuthorizationException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.AccountNotFoundException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.vo.TokenAndPersonalUserInfoVo;
import cn.hdustea.aha_server.vo.TokenVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 授权鉴权类请求
 *
 * @author STEA_YY
 **/
@RestController
@Slf4j(topic = "userOperationLog")
public class AuthController {
    protected static final String MODULE_NAME = "注册登录模块";
    @Resource
    private AuthService authService;
    @Resource
    private UserOperationLogConfig userOperationLogConfig;

    /**
     * 通过账号密码登录
     *
     * @param phoneLoginUserDto 包含账号密码的实体，从请求Json中获取
     */
    @RequestLimit(amount = 5, time = 180)
    @PostMapping("/login/phone")
    public ResponseBean<TokenAndPersonalUserInfoVo> loginByPhone(@RequestBody @Validated PhoneLoginUserDto phoneLoginUserDto) throws Exception {
        TokenAndPersonalUserInfoVo tokenAndPersonalUserInfoVo = authService.loginByPhone(phoneLoginUserDto);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "用户登录", "");
        return new ResponseBean<>(200, "登录成功", tokenAndPersonalUserInfoVo);
    }

    /**
     * 通过手机号注册
     *
     * @param phoneRegisterUserDto 包含注册信息的实体
     */
    @RequestLimit(amount = 1)
    @PostMapping("/register/phone")
    public ResponseBean<TokenAndPersonalUserInfoVo> registerByPhone(@RequestBody @Validated PhoneRegisterUserDto phoneRegisterUserDto) throws Exception {
        TokenAndPersonalUserInfoVo tokenAndPersonalUserInfoVo = authService.registerByPhone(phoneRegisterUserDto);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "用户注册", "");
        return new ResponseBean<>(200, "注册成功", tokenAndPersonalUserInfoVo);
    }

    /**
     * 绑定手机号
     *
     * @param phoneBindDto 绑定信息实体
     */
    @RequiresLogin(requireSignNotice = false)
    @RequestLimit(amount = 1)
    @PostMapping("/bind/phone")
    public ResponseBean<Object> bindPhone(@RequestBody @Validated PhoneBindDto phoneBindDto) throws MessageCheckException, AuthorizationException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        authService.bindPhone(phoneBindDto, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改密码
     *
     * @param phoneChangePasswordDto 存放修改密码相关信息的实体类
     * @param phone                  手机号
     */
    @RequestLimit(amount = 1)
    @PostMapping("/changePassword/phone/{phone}")
    public ResponseBean<Object> changePassword(@RequestBody @Validated PhoneChangePasswordDto phoneChangePasswordDto, @PathVariable("phone") String phone) throws MessageCheckException, AccountNotFoundException {
        authService.changePasswordByPhone(phoneChangePasswordDto, phone);
        return new ResponseBean<>(200, "密码修改成功！", null);
    }

    /**
     * 签署服务协议
     */
    @RequestLimit(amount = 1, time = 180)
    @RequiresLogin(requireSignNotice = false)
    @PostMapping("/sign/notice")
    public ResponseBean<TokenVo> signNotice() throws UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        String updatedToken = authService.signNotice(userId);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(updatedToken);
        return new ResponseBean<>(200, "已同意用户协议", tokenVo);
    }

    /**
     * 签署合同
     *
     * @param file     合同签名文件
     * @param contract 合同信息实体类
     */
    @RequestLimit(amount = 1, time = 180)
    @RequiresLogin
    @PostMapping("/sign/contract")
    public ResponseBean<TokenVo> signContract(MultipartFile file, @Validated Contract contract) throws IOException, UpdateException, InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        String updatedToken = authService.signContract(userId, file, contract);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(updatedToken);
        return new ResponseBean<>(200, "已签署合同", tokenVo);
    }

    /**
     * 登出
     */
    @RequiresLogin
    @GetMapping("/logout")
    public ResponseBean<Object> logout() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        authService.logout(userId);
        return new ResponseBean<>(200, "登出成功", null);
    }

    /**
     * 绑定微信账号
     *
     * @param code 小程序请求码
     */
    @RequestLimit(amount = 1, time = 180)
    @RequiresLogin(requireSignNotice = false)
    @PostMapping("/bind/wechat")
    public ResponseBean<Object> wechatBind(@RequestParam("code") String code) throws Exception {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        authService.bindWechat(userId, code);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 通过微信号登录或注册
     *
     * @param wechatRegisterUserDto 包含注册信息的实体
     */
    @RequestLimit(amount = 1,time = 180)
    @PostMapping("/login/wechat")
    public ResponseBean<TokenAndPersonalUserInfoVo> loginByWechat(@RequestBody @Validated WechatRegisterUserDto wechatRegisterUserDto) throws Exception {
        TokenAndPersonalUserInfoVo tokenAndPersonalUserInfoVo;
        tokenAndPersonalUserInfoVo = authService.LoginByWechat(wechatRegisterUserDto);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "用户登录", "");
        return new ResponseBean<>(200, "登录成功", tokenAndPersonalUserInfoVo);
    }
}