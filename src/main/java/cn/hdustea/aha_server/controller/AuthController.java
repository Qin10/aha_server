package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.LoginUser;
import cn.hdustea.aha_server.bean.RegisterUser;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
     * 用户登出的接口
     *
     * @return
     */
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
