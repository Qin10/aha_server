package cn.hdustea.aha_server.exception.runtimeApiException;

import cn.hdustea.aha_server.constants.ApiExceptionCode;
import cn.hdustea.aha_server.exception.RuntimeApiException;

/**
 * 访问次数限制异常类
 *
 * @author STEA_YY
 **/
public class RequestTimesExceededException extends RuntimeApiException {
    public RequestTimesExceededException() {
        super(ApiExceptionCode.REQUEST_TIMES_EXCEEDED.getDesc(), ApiExceptionCode.REQUEST_TIMES_EXCEEDED.getValue());
    }

    public RequestTimesExceededException(String message) {
        super(message, ApiExceptionCode.REQUEST_TIMES_EXCEEDED.getValue());
    }
}