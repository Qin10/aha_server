package cn.hdustea.aha_server.exception.daoException;

import cn.hdustea.aha_server.exception.DaoException;

/**
 * 密码错误异常类
 *
 * @author STEA_YY
 **/
public class InvalidPasswordException extends DaoException {

    public InvalidPasswordException(String message) {
        super(message);
        super.setErrorCode(502);
    }
}
