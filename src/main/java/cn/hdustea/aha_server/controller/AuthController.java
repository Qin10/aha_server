package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.*;
import cn.hdustea.aha_server.entity.Contract;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * 授权鉴权控制类
 *
 * @author STEA_YY
 **/
@RestController

public class AuthController {
    @Resource
    private AuthService authService;

    /**
     * 用户通过账号密码方式登录的接口
     *
     * @param loginUser 包含账号密码的实体，从请求Json中获取
     * @throws Exception 向上抛出异常
     */
    @PostMapping("/login")
    public ResponseBean login(@RequestBody @Validated LoginUser loginUser) throws Exception {
        TokenAndPersonalUserInfoBean tokenAndPersonalUserInfoBean = authService.login(loginUser);
        return new ResponseBean(200, "登录成功", tokenAndPersonalUserInfoBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户通过手机号注册的接口
     *
     * @param registerUser 包含注册信息的实体
     * @throws Exception 向上抛出异常
     */
    @PostMapping("/register")
    public ResponseBean register(@RequestBody @Validated RegisterUser registerUser) throws Exception {
        authService.register(registerUser);
        LoginUser loginUser = new LoginUser(registerUser.getPhone(), registerUser.getPassword());
        TokenAndPersonalUserInfoBean tokenAndPersonalUserInfoBean = authService.login(loginUser);
        return new ResponseBean(200, "注册成功", tokenAndPersonalUserInfoBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户修改密码请求
     *
     * @param changePasswordBean 存放修改密码相关信息的实体类
     * @param phone              手机号
     * @throws MessageCheckException    短信验证码校验异常
     * @throws AccountNotFoundException 账号未找到异常
     */
    @PostMapping("/changePassword/{phone}")
    public ResponseBean changePassword(@RequestBody @Validated ChangePasswordBean changePasswordBean, @PathVariable("phone") String phone) throws MessageCheckException, AccountNotFoundException {
        authService.changePassword(changePasswordBean, phone);
        return new ResponseBean(200, "密码修改成功！", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 登录用户签署服务协议的接口
     *
     * @throws AccountNotFoundException 用户不存在异常
     */
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/sign/notice")
    public ResponseBean signNotice(HttpServletRequest request) throws AccountNotFoundException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        String updatedToken = authService.signNotice(phone);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("token", updatedToken);
        return new ResponseBean(200, "已同意用户协议", responseMap, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PostMapping("/sign/contract")
    public ResponseBean signContract(HttpServletRequest request, MultipartFile file, @Validated Contract contract) throws IOException, AccountNotFoundException, UpdateException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        String updatedToken = authService.signContract(phone, file, contract);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("token", updatedToken);
        return new ResponseBean(200, "已签署合同", responseMap, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户登出的接口
     */
    @RequiresLogin
    @GetMapping("/logout")
    public ResponseBean logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        authService.logout(phone);
        return new ResponseBean(200, "登出成功", null, TimeUtil.getFormattedTime(new Date()));
    }
}
