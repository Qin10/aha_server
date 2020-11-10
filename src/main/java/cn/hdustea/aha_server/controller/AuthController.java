package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.dto.TokenAndPersonalUserInfoBean;
import cn.hdustea.aha_server.entity.Contract;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import cn.hdustea.aha_server.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

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
     * @param loginUser 包含账号密码的实体，从请求Json中获取
     * @throws Exception 向上抛出异常
     */
    @PostMapping("/login")
    public ResponseBean<TokenAndPersonalUserInfoBean> login(@RequestBody @Validated LoginUser loginUser) throws Exception {
        TokenAndPersonalUserInfoBean tokenAndPersonalUserInfoBean = authService.login(loginUser);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "用户登录", "");
        return new ResponseBean<>(200, "登录成功", tokenAndPersonalUserInfoBean);
    }

    /**
     * 通过手机号注册
     *
     * @param registerUser 包含注册信息的实体
     * @throws Exception 向上抛出异常
     */
    @PostMapping("/register")
    public ResponseBean<TokenAndPersonalUserInfoBean> register(@RequestBody @Validated RegisterUser registerUser) throws Exception {
        authService.register(registerUser);
        LoginUser loginUser = new LoginUser(registerUser.getPhone(), registerUser.getPassword());
        TokenAndPersonalUserInfoBean tokenAndPersonalUserInfoBean = authService.login(loginUser);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "用户注册", "");
        return new ResponseBean<>(200, "注册成功", tokenAndPersonalUserInfoBean);
    }

    /**
     * 修改密码
     *
     * @param changePasswordBean 存放修改密码相关信息的实体类
     * @param phone              手机号
     * @throws MessageCheckException 短信验证码校验异常
     * @throws SelectException       用户不存在异常
     */
    @PostMapping("/changePassword/{phone}")
    public ResponseBean<Object> changePassword(@RequestBody @Validated ChangePasswordBean changePasswordBean, @PathVariable("phone") String phone) throws MessageCheckException, SelectException {
        authService.changePassword(changePasswordBean, phone);
        return new ResponseBean<>(200, "密码修改成功！", null);
    }

    /**
     * 签署服务协议
     *
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/sign/notice")
    public ResponseBean<TokenBean> signNotice() throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        String updatedToken = authService.signNotice(phone);
        TokenBean tokenBean = new TokenBean();
        tokenBean.setToken(updatedToken);
        return new ResponseBean<>(200, "已同意用户协议", tokenBean);
    }

    /**
     * 签署合同
     *
     * @param file     合同签名文件
     * @param contract 合同信息实体类
     * @throws IOException     文件IO异常
     * @throws UpdateException 用户表修改异常
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @PostMapping("/sign/contract")
    public ResponseBean<TokenBean> signContract(MultipartFile file, @Validated Contract contract) throws IOException, UpdateException, SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        String updatedToken = authService.signContract(phone, file, contract);
        TokenBean tokenBean = new TokenBean();
        tokenBean.setToken(updatedToken);
        return new ResponseBean<>(200, "已签署合同", tokenBean);
    }

    /**
     * 登出
     */
    @RequiresLogin
    @GetMapping("/logout")
    public ResponseBean<Object> logout() {
        String phone = ThreadLocalUtil.getCurrentUser();
        authService.logout(phone);
        return new ResponseBean<>(200, "登出成功", null);
    }
}
