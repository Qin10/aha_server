package cn.hdustea.aha_server.exception.apiException.daoException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.ApiException;
import cn.hdustea.aha_server.exception.apiException.DaoException;

/**
 * 数据库插入失败的异常类
 *
 * @author STEA_YY
 **/
public class InsertException extends DaoException {
    public InsertException(String message) {
        super(message, ApiExceptionCode.INSERT_ERROR.getValue());
    }

    public InsertException() {
        super(ApiExceptionCode.INSERT_ERROR.getDesc(), ApiExceptionCode.INSERT_ERROR.getValue());
    }

    public InsertException(String message, Integer code) {
        super(message, code);
    }
}
