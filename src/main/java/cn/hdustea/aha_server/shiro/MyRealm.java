package cn.hdustea.aha_server.shiro;

import cn.hdustea.aha_server.dao.UserDao;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm,完成Shiro鉴权
 *
 * @author STEA_YY
 **/
@Slf4j
@Service
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;
    @Resource
    private RedisUtil redisUtil;
    private static final int REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60;
    private static final String REFRESH_TOKEN_PREFIX = "user:token:";

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String phone = JWTUtil.getAccount(principals.toString());
        User user = userDao.findUserByPhone(phone);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole().getName());
//        if (user.getPermission()!=null){
//            Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
//            simpleAuthorizationInfo.addStringPermissions(permission);
//        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String phone = JWTUtil.getAccount(token);
        if (phone == null) {
            throw new AuthenticationException("用户凭证校验失败！");
        }

        User user = userDao.findUserByPhone(phone);
        if (user == null) {
            throw new AuthenticationException("用户不存在！");
        }
        try {
            boolean verify = JWTUtil.verify(token, phone, user.getPassword());
            if (!verify) {
                throw new AuthenticationException("用户名或密码错误！");
            }
        } catch (TokenExpiredException e) {
            if (!refreshToken(token, user)) {
                throw new AuthenticationException("用户凭证已过期！");
            }
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

    /**
     * 尝试从redis中获取refreshToken信息并刷新token,返回于header中的Authorization字段。
     */
    protected boolean refreshToken(String token, User user) {
        String possibleToken = (String) redisUtil.get(REFRESH_TOKEN_PREFIX + user.getPhone());
        if (possibleToken != null && possibleToken.equals(token)) {
            String newToken = JWTUtil.sign(user.getPhone(), user.getPassword());
            redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), newToken, REFRESH_TOKEN_EXPIRE_TIME);
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setHeader("Authorization", newToken);
            return true;
        }
        return false;
    }
}
