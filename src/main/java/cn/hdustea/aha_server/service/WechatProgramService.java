package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.Oauth;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.exception.apiException.authenticationException.WechatUnauthorizedException;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import cn.hdustea.aha_server.util.WechatUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

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
    private RedisUtil redisUtil;
    private static final int REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60;
    private static final String REFRESH_TOKEN_PREFIX = "user:token:";

    /**
     * 使用微信请求code完成登录校验
     *
     * @param code 微信请求code
     * @return token令牌
     * @throws WechatUnauthorizedException 微信小程序授权信息未找到异常
     * @throws IOException IO操作异常
     */
    public String wechatLogin(String code) throws WechatUnauthorizedException, IOException {
        String openId;
        openId = WechatUtil.getWxInfo(code).getOpenId();
        Oauth wechatOauth = oauthService.getOauthByOauthTypeAndOauthId("wechat", openId);
        if (wechatOauth == null) {
            throw new WechatUnauthorizedException();
        } else {
            User user = wechatOauth.getUser();
            String token = JWTUtil.sign(REFRESH_TOKEN_PREFIX + user.getPhone(), user.getPassword());
            redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), token, REFRESH_TOKEN_EXPIRE_TIME);
            return token;
        }
    }
}
