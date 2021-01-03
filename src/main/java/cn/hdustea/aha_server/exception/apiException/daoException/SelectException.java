package cn.hdustea.aha_server.exception.apiException.daoException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.DaoException;

/**
 * 数据库插入失败的异常类
 *
 * @author STEA_YY
 **/
public class SelectException extends DaoException {
    public SelectException(String message) {
        super(message, ApiExceptionCodes.SELECT_ERROR.getValue());
    }

    public SelectException() {
        super(ApiExceptionCodes.INSERT_ERROR.getDesc(), ApiExceptionCodes.SELECT_ERROR.getValue());
    }

    public SelectException(String message, Integer code) {
        super(message, code);
    }
}
