package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.ActivityCodeExchangeLog;

/**
* ${description}
*
* @author STEA_YY
**/
public interface ActivityCodeExchangeLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityCodeExchangeLog record);

    int insertSelective(ActivityCodeExchangeLog record);

    ActivityCodeExchangeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityCodeExchangeLog record);

    int updateByPrimaryKey(ActivityCodeExchangeLog record);
}