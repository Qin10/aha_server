package cn.hdustea.aha_server.exception;

/**
 * @program: aha_server
 * @description: 请求被拒绝的错误类
 * @author: HduStea_YY
 * @create: 2020-10-09 22:55
 **/
public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String msg) {
        super(msg);
    }

    public ForbiddenException() {
        super();
    }
}
