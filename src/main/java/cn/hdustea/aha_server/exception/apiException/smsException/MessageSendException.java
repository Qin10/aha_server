package cn.hdustea.aha_server.exception.apiException.smsException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.SmsException;

/**
 * 短信发送失败的异常类
 *
 * @author STEA_YY
 **/
public class MessageSendException extends SmsException {
    public MessageSendException() {
        super(ApiExceptionCodes.MESSAGE_SEND_FAIL.getDesc(), ApiExceptionCodes.MESSAGE_SEND_FAIL.getValue());
    }

    public MessageSendException(String message) {
        super(message, ApiExceptionCodes.MESSAGE_SEND_FAIL.getValue());
    }
}
