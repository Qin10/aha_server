package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.UserOperationLog;

/**
 * 用户行为日志的数据库操作接口
 *
 * @author STEA_YY
 **/
public interface UserOperationLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOperationLog record);

    int insertSelective(UserOperationLog record);

    UserOperationLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOperationLog record);

    int updateByPrimaryKey(UserOperationLog record);
}