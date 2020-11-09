package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.JwtPayloadBean;
import cn.hdustea.aha_server.config.JWTConfig;
import cn.hdustea.aha_server.entity.Oauth;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.exception.apiException.authenticationException.WechatUnauthorizedException;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import cn.hdustea.aha_server.util.WechatUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.hdustea.aha_server.util.RedisUtil.REFRESH_TOKEN_PREFIX;

/**
 * 微信小程序授权/鉴权服务类
 *
 * @author STEA_YY
 **/
@Service
public class WechatProgramService {
    @Resource
    private OauthService oauthService;
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private JWTConfig jwtConfig;

    /**
     * 使用微信请求code完成登录校验
     *
     * @param code 微信请求code
     * @return token令牌
     * @throws WechatUnauthorizedException 微信小程序授权信息未找到异常
     */
    public String wechatLogin(String code) throws WechatUnauthorizedException {
        String openId;
        openId = WechatUtil.getWxInfo(code).getOpenId();
        Oauth wechatOauth = oauthService.getOauthByOauthTypeAndOauthId("wechat", openId);
        if (wechatOauth == null) {
            throw new WechatUnauthorizedException();
        } else {
            Integer userId = wechatOauth.getUserId();
            User user = userService.getUserById(userId);
            JwtPayloadBean jwtPayloadBean = JWTUtil.packagePayload(user);
            String token = JWTUtil.sign(jwtPayloadBean, jwtConfig.getSecret(), jwtConfig.getExpireTime());
            redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), token, jwtConfig.getRefreshTokenExpireTime());
            return token;
        }
    }
}
