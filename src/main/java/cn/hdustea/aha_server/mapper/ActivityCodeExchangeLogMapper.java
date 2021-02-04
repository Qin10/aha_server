package cn.hdustea.aha_server.mapper;
import cn.hdustea.aha_server.entity.ActivityCodeExchangeLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<ActivityCodeExchangeLog> selectAllByConditions(@Param("userId")Integer userId,@Param("activityId")Integer activityId);


}