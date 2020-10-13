package cn.hdustea.aha_server.exception;

/**
 * 鉴权相关的异常类
 *
 * @author STEA_YY
 **/
public class AuthenticationException extends Exception {

    public AuthenticationException(String message) {
        super(message);
    }
}
