package cn.hdustea.aha_server.exception;

/**
 * @program: aha_server
 * @description: 用户不存在异常
 * @author: HduStea_YY
 * @create: 2020-10-10 00:57
 **/
public class UserNotFoundException extends Exception{

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
