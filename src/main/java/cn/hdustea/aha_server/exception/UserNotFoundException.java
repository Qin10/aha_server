package cn.hdustea.aha_server.exception;

/**
 * 用户未找到异常类
 *
 * @author STEA_YY
 **/
public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
