package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * token校验的异常类
 *
 * @author STEA_YY
 **/
public class TokenCheckException extends AuthenticationException {
    public TokenCheckException() {
        super(ApiExceptionCodes.TOKEN_CHECK_FAIL.getDesc(), ApiExceptionCodes.TOKEN_CHECK_FAIL.getValue());
    }

    public TokenCheckException(String message) {
        super(message, ApiExceptionCodes.TOKEN_CHECK_FAIL.getValue());
    }
}
