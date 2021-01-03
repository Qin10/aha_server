package cn.hdustea.aha_server.exception.apiException.daoException.insertException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;

/**
 * 用户已存在异常类
 *
 * @author STEA_YY
 **/
public class AccountExistedException extends InsertException {
    public AccountExistedException(String message) {
        super(message, ApiExceptionCodes.ACCOUNT_EXISTED.getValue());
    }

    public AccountExistedException() {
        super(ApiExceptionCodes.ACCOUNT_EXISTED.getDesc(), ApiExceptionCodes.ACCOUNT_EXISTED.getValue());
    }
}
