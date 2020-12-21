package cn.hdustea.aha_server.exception.apiException.smsException;

import cn.hdustea.aha_server.constants.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.SmsException;

/**
 * 短信发送失败的异常类
 *
 * @author STEA_YY
 **/
public class MessageSendException extends SmsException {
    public MessageSendException() {
        super(ApiExceptionCode.MESSAGE_SEND_FAIL.getDesc(), ApiExceptionCode.MESSAGE_SEND_FAIL.getValue());
    }

    public MessageSendException(String message) {
        super(message, ApiExceptionCode.MESSAGE_SEND_FAIL.getValue());
    }
}
