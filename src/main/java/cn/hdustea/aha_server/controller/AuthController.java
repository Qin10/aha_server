package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ChangePasswordBean;
import cn.hdustea.aha_server.bean.LoginUser;
import cn.hdustea.aha_server.bean.RegisterUser;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.exception.apiException.DaoException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageCheckException;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
     * @return HTTP响应实体类
     * @throws Exception 向上抛出异常
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
     * @return HTTP响应实体类
     * @throws DaoException          数据库操作异常
     * @throws MessageCheckException 短信验证码校验异常
     */
    @PostMapping("/register")
    public ResponseBean register(@RequestBody RegisterUser registerUser) throws MessageCheckException, DaoException {
        authService.register(registerUser);
        return new ResponseBean(200, "注册成功", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户修改密码请求
     *
     * @param changePasswordBean 存放修改密码相关信息的实体类
     * @param phone              手机号
     * @return HTTP响应实体类
     * @throws MessageCheckException    短信验证码校验异常
     * @throws AccountNotFoundException 账号未找到异常
     */
    @PostMapping("/changePassword/{phone}")
    public ResponseBean changePassword(@RequestBody ChangePasswordBean changePasswordBean, @PathVariable("phone") String phone) throws MessageCheckException, AccountNotFoundException {
        authService.changePassword(changePasswordBean, phone);
        return new ResponseBean(200, "密码修改成功！", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户登出的接口
     *
     * @return HTTP响应实体类
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
