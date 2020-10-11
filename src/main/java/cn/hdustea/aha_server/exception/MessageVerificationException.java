package cn.hdustea.aha_server.exception;

/**
 * @program: aha_server
 * @description: 短信验证相关异常
 * @author: HduStea_YY
 * @create: 2020-10-11 21:44
 **/
public class MessageVerificationException extends Exception {
    public MessageVerificationException() {
    }

    public MessageVerificationException(String message) {
        super(message);
    }
}
