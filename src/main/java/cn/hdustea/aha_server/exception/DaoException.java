package cn.hdustea.aha_server.exception;

import lombok.Data;

/**
 * 数据库操作异常类
 *
 * @author STEA_YY
 **/
@Data
public class DaoException extends Exception {
    private Integer errorCode;

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
