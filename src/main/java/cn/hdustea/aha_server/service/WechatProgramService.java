package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.Oauth;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import cn.hdustea.aha_server.util.WechatUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    public String wechatLogin(String code) throws Exception {
        String openId = WechatUtil.getWxInfo(code).getOpenId();
        Oauth wechatOauth = oauthService.getOauthByOauthTypeAndOauthId("wechat", openId);
        if (wechatOauth == null) {
            throw new UnauthorizedException("未找到授权信息");
        } else {
            User user = wechatOauth.getUser();
            String token = JWTUtil.sign(REFRESH_TOKEN_PREFIX + user.getPhone(), user.getPassword());
            redisUtil.set(REFRESH_TOKEN_PREFIX + user.getPhone(), token, REFRESH_TOKEN_EXPIRE_TIME);
            return token;
        }
    }
}
