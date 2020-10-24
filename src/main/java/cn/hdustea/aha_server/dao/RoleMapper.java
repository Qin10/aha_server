package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.Role;
import org.springframework.stereotype.Component;

/**
* ${description}
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