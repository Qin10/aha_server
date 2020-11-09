package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Role;

/**
 * 角色表mapper
 *
 * @author STEA_YY
 **/
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}