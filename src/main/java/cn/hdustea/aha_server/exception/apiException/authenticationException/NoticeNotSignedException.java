package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.constants.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 用户未签署服务协议的异常类
 *
 * @author STEA_YY
 **/
public class NoticeNotSignedException extends AuthenticationException {
    public NoticeNotSignedException() {
        super(ApiExceptionCode.NOTICE_NOT_SIGNED.getDesc(), ApiExceptionCode.NOTICE_NOT_SIGNED.getValue());
    }

    public NoticeNotSignedException(String message) {
        super(message, ApiExceptionCode.NOTICE_NOT_SIGNED.getValue());
    }
}
