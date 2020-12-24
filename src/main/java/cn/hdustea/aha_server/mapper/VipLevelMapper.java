package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.VipLevel;

/**
* ${description}
*
* @author STEA_YY
**/
public interface VipLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipLevel record);

    int insertSelective(VipLevel record);

    VipLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipLevel record);

    int updateByPrimaryKey(VipLevel record);
}