package cn.hdustea.aha_server.exception.daoException;

import cn.hdustea.aha_server.exception.DaoException;

/**
 * 用户未找到异常类
 *
 * @author STEA_YY
 **/
public class UserNotFoundException extends DaoException {


    public UserNotFoundException(String message) {
        super(message);
        super.setErrorCode(501);
    }
}
