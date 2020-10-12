package cn.hdustea.aha_server.exception;

/**
 * 数据库操作异常类
 *
 * @author STEA_YY
 **/
public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }
}
