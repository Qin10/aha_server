package cn.hdustea.aha_server.exception.apiException.daoException;

import cn.hdustea.aha_server.constants.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.DaoException;

/**
 * 数据库修改失败的异常类
 *
 * @author STEA_YY
 **/
public class UpdateException extends DaoException {
    public UpdateException() {
        super(ApiExceptionCode.UPDATE_ERROR.getDesc(), ApiExceptionCode.UPDATE_ERROR.getValue());
    }

    public UpdateException(String message) {
        super(message, ApiExceptionCode.UPDATE_ERROR.getValue());
    }
}
