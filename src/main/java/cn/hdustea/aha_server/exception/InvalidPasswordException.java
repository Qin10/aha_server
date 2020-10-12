package cn.hdustea.aha_server.exception;

/**
 * 密码错误异常类
 *
 * @author STEA_YY
 **/
public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
