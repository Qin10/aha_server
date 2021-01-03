package cn.hdustea.aha_server.exception.apiException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.ApiException;

/**
 * 授权异常类
 *
 * @author STEA_YY
 **/
public class AuthorizationException extends ApiException {
    public AuthorizationException() {
        super(ApiExceptionCodes.AUTHORIZATION_FAIL.getDesc(), ApiExceptionCodes.AUTHORIZATION_FAIL.getValue());
    }

    public AuthorizationException(String message) {
        super(message, ApiExceptionCodes.AUTHORIZATION_FAIL.getValue());
    }
}
