package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ChangePasswordBean;
import cn.hdustea.aha_server.bean.LoginUser;
import cn.hdustea.aha_server.bean.RegisterUser;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.service.UserService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
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
     * @return HTTP响应
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseBean login(@RequestBody LoginUser loginUser) throws Exception {
        String token = authService.login(loginUser);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("token", token);
        return new ResponseBean(200, "登录成功", responseMap, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户通过手机号注册的接口
     *
     * @param registerUser 包含注册信息的实体
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    public ResponseBean register(@RequestBody RegisterUser registerUser) throws Exception {
        authService.register(registerUser);
        return new ResponseBean(200, "注册成功", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户修改密码请求
     *
     * @param request            HTTP请求
     * @param changePasswordBean 存放修改密码相关信息的实体类
     * @return
     * @throws MessageCheckException    短信验证码校验异常
     * @throws AccountNotFoundException 账号未找到异常
     */
    @PostMapping("/changePassword")
    public ResponseBean changePassword(HttpServletRequest request, @RequestBody ChangePasswordBean changePasswordBean) throws MessageCheckException, AccountNotFoundException {
        authService.changePassword(changePasswordBean);
        return new ResponseBean(200, "密码修改成功！", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户登出的接口
     *
     * @return
     */
    @RequiresLogin
    @GetMapping("/logout")
    public ResponseBean logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getAccount(token);
        authService.logout(phone);
        return new ResponseBean(200, "登出成功", null, TimeUtil.getFormattedTime(new Date()));
    }
}
