package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.LoginUser;
import cn.hdustea.aha_server.bean.RegisterUser;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.exception.ForbiddenException;
import cn.hdustea.aha_server.exception.InvalidPasswordException;
import cn.hdustea.aha_server.exception.UserNotFoundException;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.service.UserService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * @program: aha_server
 * @description: 授权鉴权控制类
 * @author: HduStea_YY
 * @create: 2020-10-10 00:51
 **/
@RestController

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseBean login(@RequestBody LoginUser loginUser) throws Exception {
        String token = authService.login(loginUser);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("token", token);
        return new ResponseBean(200, "登录成功", responseMap, TimeUtil.getFormattedTime(new Date()));
    }

    @PostMapping("/register")
    public ResponseBean register(@RequestBody RegisterUser registerUser) throws Exception {
        authService.register(registerUser);
        return new ResponseBean(200, "注册成功", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public ResponseBean logout() {
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        String phone = JWTUtil.getAccount(token);
        authService.logout(phone);
        return new ResponseBean(200, "登出成功", null, TimeUtil.getFormattedTime(new Date()));
    }
}
