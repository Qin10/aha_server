package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 微信未找到授权信息异常类
 *
 * @author STEA_YY
 **/
public class WechatUnauthorizedException extends AuthenticationException {
    public WechatUnauthorizedException() {
        super(ApiExceptionCode.WECHAT_UNAUTHORIZED.getDesc(), ApiExceptionCode.WECHAT_UNAUTHORIZED.getValue());
    }

    public WechatUnauthorizedException(String message) {
        super(message, ApiExceptionCode.WECHAT_UNAUTHORIZED.getValue());
    }
}
