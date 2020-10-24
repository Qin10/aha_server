package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.Resource;
import org.springframework.stereotype.Component;

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
}