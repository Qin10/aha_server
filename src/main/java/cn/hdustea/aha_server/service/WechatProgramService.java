package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.Oauth;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.enums.OauthType;
import cn.hdustea.aha_server.exception.apiException.AuthorizationException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.WechatUnauthorizedException;
import cn.hdustea.aha_server.util.IpUtil;
import cn.hdustea.aha_server.util.WechatUtil;
import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import cn.hdustea.aha_server.vo.TokenAndPersonalUserInfoVo;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    private UserInfoService userInfoService;
    @Resource
    private AuthService authService;
    @Resource
    private HttpServletRequest request;

    /**
     * 使用微信请求code完成登录校验
     *
     * @param code 微信请求code
     * @return token令牌
     * @throws WechatUnauthorizedException 微信小程序授权信息未找到异常
     */
    public TokenAndPersonalUserInfoVo wechatLogin(String code) throws Exception {
        String openid = WechatUtil.getWxInfo(code).getOpenid();
        Oauth wechatOauth = oauthService.getOauthByOauthTypeAndOauthId(OauthType.WECHAT.getValue(), openid);
        if (wechatOauth == null) {
            throw new WechatUnauthorizedException();
        } else {
            MDC.put("id", wechatOauth.getUserId().toString());
            MDC.put("ip", IpUtil.getIpAddr(request));
            User user = userService.getUserById(wechatOauth.getUserId());
            String token = authService.signToken(user);
            PersonalUserInfoVo personalUserInfo = userInfoService.getPersonalUserInfo(user.getId());
            return new TokenAndPersonalUserInfoVo(token, personalUserInfo);
        }
    }

    public void wechatBind(int userId, String code) throws Exception {
        String openid = WechatUtil.getWxInfo(code).getOpenid();
        if (openid == null) {
            throw new AuthorizationException("非法授权码！");
        }
        if (oauthService.getOauthByOauthTypeAndUserId(OauthType.WECHAT.getValue(), userId) != null) {
            throw new AuthorizationException("您已绑定过微信账号！");
        }
        if (oauthService.getOauthByOauthTypeAndOauthId(OauthType.WECHAT.getValue(), openid) != null) {
            throw new AuthorizationException("该微信账号已被绑定过！");
        }
        Oauth oauth = new Oauth();
        oauth.setUserId(userId);
        oauth.setOauthType(OauthType.WECHAT.getValue());
        oauth.setOauthId(openid);
        oauthService.saveOauth(oauth);
    }
}
