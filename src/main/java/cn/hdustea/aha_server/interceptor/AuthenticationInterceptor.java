package cn.hdustea.aha_server.interceptor;

import cn.hdustea.aha_server.annotation.PassAuthentication;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.exception.apiException.authenticationException.JwtExpiredException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.TokenCheckException;
import cn.hdustea.aha_server.service.UserService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 鉴权拦截器
 *
 * @author STEA_YY
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;
    private static final int REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60;
    private static final String REFRESH_TOKEN_PREFIX = "user:token:";

    /**
     * 处理鉴权请求
     *
     * @param request  用户请求
     * @param response HTTP响应
     * @param handler  被拦截对象
     * @return 是否通过
     * @throws Exception 向上抛出异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassAuthentication.class)) {
            PassAuthentication passAuthentication = method.getAnnotation(PassAuthentication.class);
            if (passAuthentication.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(RequiresLogin.class)) {
            RequiresLogin requiresLogin = method.getAnnotation(RequiresLogin.class);
            if (requiresLogin.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                String phone = JWTUtil.getAccount(token);
                if (phone == null) {
                    throw new TokenCheckException();
                }

                User user = userService.getUserByPhone(phone);
                if (user == null) {
                    throw new TokenCheckException();
                }
                try {
                    boolean verify = JWTUtil.verify(token, phone, user.getPassword());
                    if (!verify) {
                        throw new TokenCheckException();
                    }
                } catch (TokenExpiredException e) {
                    if (!refreshToken(token, user)) {
                        throw new JwtExpiredException();
                    }
                }
                return true;
            }
        }
        return true;
    }

    /**
     * 尝试刷新用户token
     *
     * @param token 过期token
     * @param user  当前用户
     * @return 是否刷新成功
     */
    protected boolean refreshToken(String token, User user) {
        String possibleToken = (String) redisUtil.get(REFRESH_TOKEN_PREFIX + user.getPhone());
        if (possibleToken != null && possibleToken.equals(token)) {
            String newToken = JWTUtil.sign(user.getPhone(), user.getPassword());
            redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), newToken, REFRESH_TOKEN_EXPIRE_TIME);
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response;
            if (requestAttributes != null) {
                response = requestAttributes.getResponse();
                if (response != null) {
                    response.setHeader("Authorization", newToken);
                }
            }

            return true;
        }
        return false;
    }
}
