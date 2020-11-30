package cn.hdustea.aha_server.exception.apiException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.ApiException;

/**
 * 授权异常类
 *
 * @author STEA_YY
 **/
public class AuthorizationException extends ApiException {
    public AuthorizationException() {
        super(ApiExceptionCode.AUTHORIZATION_FAIL.getDesc(), ApiExceptionCode.AUTHORIZATION_FAIL.getValue());
    }

    public AuthorizationException(String message) {
        super(message, ApiExceptionCode.AUTHORIZATION_FAIL.getValue());
    }
}
