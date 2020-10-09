package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.Role;
import cn.hdustea.aha_server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleDao {
    @Select("SELECT * FROM role")
    List<Role> findAllRoles();

    @Select("SELECT * FROM role WHERE id = #{id}")
    Role findRoleById(@Param("id") String id);
}
