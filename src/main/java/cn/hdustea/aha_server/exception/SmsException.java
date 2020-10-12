package cn.hdustea.aha_server.exception;

/**
 * 短信业务异常类
 *
 * @author STEA_YY
 **/
public class SmsException extends Exception {
    public SmsException() {
        super();
    }

    public SmsException(String message) {
        super(message);
    }
}
