package cn.hdustea.aha_server.exception.apiException.daoException.insertException;

import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;

/**
 * 用户已存在异常类
 *
 * @author STEA_YY
 **/
public class AccountExistedException extends InsertException {
    public AccountExistedException(String message) {
        super(message, ApiExceptionCode.ACCOUNT_EXISTED.getValue());
    }

    public AccountExistedException() {
        super(ApiExceptionCode.ACCOUNT_EXISTED.getDesc(), ApiExceptionCode.ACCOUNT_EXISTED.getValue());
    }
}
