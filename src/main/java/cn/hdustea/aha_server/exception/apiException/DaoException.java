package cn.hdustea.aha_server.exception.apiException;

import cn.hdustea.aha_server.exception.ApiException;

/**
 * 数据库操作异常类
 *
 * @author STEA_YY
 **/
public class DaoException extends ApiException {
    public DaoException() {
    }

    public DaoException(String message, Integer code) {
        super(message, code);
    }
}
