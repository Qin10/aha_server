package cn.hdustea.aha_server.exception;

/**
 * 禁止访问异常类，对应HTTP403
 *
 * @author STEA_YY
 **/
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg) {
        super(msg);
    }

    public ForbiddenException() {
        super();
    }
}
