package cn.hdustea.aha_server.exception;

/**
 * 短信验证码验证失败异常类
 *
 * @author STEA_YY
 **/
public class MessageVerificationException extends Exception {
    public MessageVerificationException() {
    }

    public MessageVerificationException(String message) {
        super(message);
    }
}
