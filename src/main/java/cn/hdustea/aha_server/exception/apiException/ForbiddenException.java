package cn.hdustea.aha_server.exception.apiException;

import cn.hdustea.aha_server.constants.ApiExceptionCode;
import cn.hdustea.aha_server.exception.ApiException;

/**
 * 鉴权错误异常类，对应HTTP403
 *
 * @author STEA_YY
 **/
public class ForbiddenException extends ApiException {
    public ForbiddenException() {
        super(ApiExceptionCode.ACCESS_FORBIDDEN.getDesc(), ApiExceptionCode.ACCESS_FORBIDDEN.getValue());
    }

    public ForbiddenException(String message) {
        super(message, ApiExceptionCode.ACCESS_FORBIDDEN.getValue());
    }
}
