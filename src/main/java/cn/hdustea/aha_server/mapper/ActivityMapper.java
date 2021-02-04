package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Activity;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);
}