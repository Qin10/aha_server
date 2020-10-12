package cn.hdustea.aha_server.exception.daoException;

import cn.hdustea.aha_server.exception.DaoException;

/**
 * 数据库插入失败的异常类
 *
 * @author STEA_YY
 **/
public class InsertException extends DaoException {
    public InsertException(String message) {
        super(message);
        super.setErrorCode(503);
    }
}
