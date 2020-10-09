package cn.hdustea.aha_server.exception;

/**
 * @program: aha_server
 * @description: 密码验证失败异常
 * @author: HduStea_YY
 * @create: 2020-10-10 01:00
 **/
public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
