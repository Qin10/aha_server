package cn.hdustea.aha_server.exception.daoException;

import cn.hdustea.aha_server.exception.DaoException;

/**
 * 数据库删除失败的异常类
 *
 * @author STEA_YY
 **/
public class DeleteException extends DaoException {
    public DeleteException(String message) {
        super(message);
        super.setErrorCode(504);
    }
}
