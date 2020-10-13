package cn.hdustea.aha_server.exception.apiException;

import cn.hdustea.aha_server.exception.ApiException;

/**
 * 鉴权相关的异常类
 *
 * @author STEA_YY
 **/
public class AuthenticationException extends ApiException {

    public AuthenticationException() {
    }

    public AuthenticationException(String message, Integer code) {
        super(message, code);
    }
}
