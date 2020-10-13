package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 密码错误异常类
 *
 * @author STEA_YY
 **/
public class InvalidPasswordException extends AuthenticationException {

    public InvalidPasswordException(String message) {
        super(message, ApiExceptionCode.INVALID_USER_PASSWORD.getValue());
    }

    public InvalidPasswordException() {
        super(ApiExceptionCode.INVALID_USER_PASSWORD.getDesc(), ApiExceptionCode.INVALID_USER_PASSWORD.getValue());
    }
}
