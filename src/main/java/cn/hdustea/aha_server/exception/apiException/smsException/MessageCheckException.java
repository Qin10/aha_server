package cn.hdustea.aha_server.exception.apiException.smsException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.SmsException;

/**
 * 短信验证码验证失败异常类
 *
 * @author STEA_YY
 **/
public class MessageCheckException extends SmsException {

    public MessageCheckException() {
        super(ApiExceptionCodes.MESSAGE_CHECK_FAIL.getDesc(), ApiExceptionCodes.MESSAGE_CHECK_FAIL.getValue());
    }

    public MessageCheckException(String message) {
        super(message, ApiExceptionCodes.MESSAGE_CHECK_FAIL.getValue());
    }
}
