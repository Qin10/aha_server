package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.LoginUser;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.exception.InvalidPasswordException;
import cn.hdustea.aha_server.exception.UserNotFoundException;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @program: aha_server
 * @description: 授权鉴权服务类
 * @author: HduStea_YY
 * @create: 2020-10-10 02:07
 **/
@Service
public class AuthService {
    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String login(LoginUser loginUser) throws Exception {
        User user = userService.getUserByPhone(loginUser.getAccount());
        if (user != null) {
            if (bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
                String token = JWTUtil.sign(user.getPhone(), user.getPassword());
                return token;
            } else {
                throw new InvalidPasswordException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    public boolean register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoleId(1);
        user.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        userService.saveUser(user);
        return true;
    }
}
