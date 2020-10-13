package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 用户未找到异常类
 *
 * @author STEA_YY
 **/
public class UserNotFoundException extends AuthenticationException {

    public UserNotFoundException(String message) {
        super(message, ApiExceptionCode.USER_NOT_FOUND.getValue());
    }

    public UserNotFoundException() {
        super(ApiExceptionCode.USER_NOT_FOUND.getDesc(), ApiExceptionCode.USER_NOT_FOUND.getValue());
    }
}
