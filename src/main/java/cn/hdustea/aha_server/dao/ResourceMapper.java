package cn.hdustea.aha_server.dao;
import org.apache.ibatis.annotations.Param;

import cn.hdustea.aha_server.entity.Resource;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    Integer selectAuthorUserIdById(@Param("id")Integer id);


}