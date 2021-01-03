package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 用户未找到异常类
 *
 * @author STEA_YY
 **/
public class AccountNotFoundException extends AuthenticationException {

    public AccountNotFoundException(String message) {
        super(message, ApiExceptionCodes.USER_NOT_FOUND.getValue());
    }

    public AccountNotFoundException() {
        super(ApiExceptionCodes.USER_NOT_FOUND.getDesc(), ApiExceptionCodes.USER_NOT_FOUND.getValue());
    }
}
