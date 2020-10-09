package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserInfoDao {
    @Select("SELECT * FROM user_info WHERE id = #{id}")
    @Results({@Result(property = "user", column = "user_id", one = @One(select = "cn.hdustea.aha_server.dao.UserDao.findUserById", fetchType = FetchType.EAGER))})
    UserInfo findUserInfoByUserId(@Param("id") int id);
}
