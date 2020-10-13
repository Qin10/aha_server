package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * token校验的异常类
 *
 * @author STEA_YY
 **/
public class TokenCheckException extends AuthenticationException {
    public TokenCheckException() {
        super(ApiExceptionCode.TOKEN_CHECK_FAIL.getDesc(), ApiExceptionCode.TOKEN_CHECK_FAIL.getValue());
    }

    public TokenCheckException(String message) {
        super(message, ApiExceptionCode.TOKEN_CHECK_FAIL.getValue());
    }
}
