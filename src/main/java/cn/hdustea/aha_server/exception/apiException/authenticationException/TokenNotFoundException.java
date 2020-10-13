package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 未找到token字段的异常类
 *
 * @author STEA_YY
 **/
public class TokenNotFoundException extends AuthenticationException {
    public TokenNotFoundException() {
        super(ApiExceptionCode.TOKEN_NOT_FOUND.getDesc(), ApiExceptionCode.TOKEN_NOT_FOUND.getValue());
    }

    public TokenNotFoundException(String message) {
        super(message, ApiExceptionCode.TOKEN_NOT_FOUND.getValue());
    }
}
