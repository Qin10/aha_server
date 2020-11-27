package cn.hdustea.aha_server.exception.apiException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.ApiException;

/**
 * 访问次数限制异常类
 *
 * @author STEA_YY
 **/
public class RequestTimesExceededException extends ApiException {
    public RequestTimesExceededException() {
        super(ApiExceptionCode.REQUEST_TIMES_EXCEEDED.getDesc(), ApiExceptionCode.REQUEST_TIMES_EXCEEDED.getValue());
    }

    public RequestTimesExceededException(String message) {
        super(message, ApiExceptionCode.REQUEST_TIMES_EXCEEDED.getValue());
    }
}
