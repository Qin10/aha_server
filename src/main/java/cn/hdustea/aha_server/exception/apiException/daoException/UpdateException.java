package cn.hdustea.aha_server.exception.apiException.daoException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.DaoException;

/**
 * 数据库修改失败的异常类
 *
 * @author STEA_YY
 **/
public class UpdateException extends DaoException {
    public UpdateException() {
        super(ApiExceptionCodes.UPDATE_ERROR.getDesc(), ApiExceptionCodes.UPDATE_ERROR.getValue());
    }

    public UpdateException(String message) {
        super(message, ApiExceptionCodes.UPDATE_ERROR.getValue());
    }
}
