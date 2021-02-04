package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 未进行实名认证异常
 *
 * @author STEA_YY
 **/
public class NotAuthenticatedException extends AuthenticationException {
    public NotAuthenticatedException() {
        super(ApiExceptionCodes.NOT_AUTHENTICATED.getDesc(), ApiExceptionCodes.NOT_AUTHENTICATED.getValue());
    }

    public NotAuthenticatedException(String message) {
        super(message, ApiExceptionCodes.NOT_AUTHENTICATED.getValue());
    }
}