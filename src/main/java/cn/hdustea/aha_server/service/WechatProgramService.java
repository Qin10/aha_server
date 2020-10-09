package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.Oauth;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.WechatUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: aha_server
 * @description: 微信小程序授权/鉴权服务类
 * @author: HduStea_YY
 * @create: 2020-10-10 03:35
 **/
@Service
public class WechatProgramService {
    @Autowired
    private OauthService oauthService;

    public String wechatLogin(String code) throws Exception {
        String openId = WechatUtil.getWxInfo(code).getOpenId();
        Oauth wechatOauth = oauthService.getOauthByOauthTypeAndOauthId("wechat", openId);
        if (wechatOauth == null) {
            throw new UnauthorizedException("未找到授权信息");
        } else {
            User user = wechatOauth.getUser();
            String token = JWTUtil.sign(user.getPhone(), user.getPassword());
            return token;
        }
    }
}
