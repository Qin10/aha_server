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

    @Insert("insert into user (phone,password,created_time,role_id) values (#{phone},#{password},#{createdTime},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveUser(User user);

    @Update("update user set password = #{newPassword} where phone = #{phone}")
    void updatePassword(@Param("phone") String phone, @Param("newPassword") String password);
}
