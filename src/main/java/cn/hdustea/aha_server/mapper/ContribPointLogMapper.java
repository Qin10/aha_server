package cn.hdustea.aha_server.mapper;
import cn.hdustea.aha_server.entity.ContribPointLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ContribPointLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContribPointLog record);

    int insertSelective(ContribPointLog record);

    ContribPointLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContribPointLog record);

    int updateByPrimaryKey(ContribPointLog record);

    List<ContribPointLog> selectAllByUserIdOrderByTimeDesc(@Param("userId")Integer userId);
}