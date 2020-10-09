package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    @Select("SELECT * FROM user")
    @Results({
            @Result(property = "role", column = "role_id", one = @One(select = "cn.hdustea.aha_server.dao.RoleDao.findRoleById"))
    })
    List<User> findAllUsers();

    @Select("SELECT * FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "role", column = "role_id", one = @One(select = "cn.hdustea.aha_server.dao.RoleDao.findRoleById"))
    })
    User findUserById(@Param("id") String id);

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    @Results({
            @Result(property = "role", column = "role_id", one = @One(select = "cn.hdustea.aha_server.dao.RoleDao.findRoleById"))
    })
    User findUserByPhone(@Param("phone") String phone);

    @Insert("insert into user (username,phone,password,created_time,avatar_url,role_id) values (#{username},#{phone},#{password},#{createdTime},#{avatarUrl},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveUser(User user);

}
