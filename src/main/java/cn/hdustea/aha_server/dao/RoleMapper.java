package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.Role;
import org.springframework.stereotype.Component;

/**
 * 用户角色的数据库操作接口
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