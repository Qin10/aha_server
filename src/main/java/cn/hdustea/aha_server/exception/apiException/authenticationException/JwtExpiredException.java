package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * token过期异常类
 *
 * @author STEA_YY
 **/
public class JwtExpiredException extends AuthenticationException {
    public JwtExpiredException() {
        super(ApiExceptionCode.TOKEN_EXPIRED.getDesc(), ApiExceptionCode.TOKEN_EXPIRED.getValue());
    }

    public JwtExpiredException(String message) {
        super(message, ApiExceptionCode.TOKEN_EXPIRED.getValue());
    }
}
