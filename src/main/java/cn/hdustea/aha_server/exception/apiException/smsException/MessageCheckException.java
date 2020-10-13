package cn.hdustea.aha_server.exception.apiException.smsException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.SmsException;

/**
 * 短信验证码验证失败异常类
 *
 * @author STEA_YY
 **/
public class MessageCheckException extends SmsException {

    public MessageCheckException() {
        super(ApiExceptionCode.MESSAGE_CHECK_FAIL.getDesc(), ApiExceptionCode.MESSAGE_CHECK_FAIL.getValue());
    }

    public MessageCheckException(String message) {
        super(message, ApiExceptionCode.MESSAGE_CHECK_FAIL.getValue());
    }
}
