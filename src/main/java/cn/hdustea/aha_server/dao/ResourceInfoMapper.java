package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.ResourceInfo;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ResourceInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceInfo record);

    int insertSelective(ResourceInfo record);

    ResourceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceInfo record);

    int updateByPrimaryKey(ResourceInfo record);
}