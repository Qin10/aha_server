package cn.hdustea.aha_server.exception;

/**
 * @program: aha_server
 * @description: 数据库相关异常
 * @author: HduStea_YY
 * @create: 2020-10-10 10:34
 **/
public class DaoException extends Exception{

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }
}
