package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserInfoDao {
    @Select("SELECT * FROM user_info WHERE id = #{id}")
    UserInfo findUserInfoById(@Param("id") int id);

    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo findUserInfoByUserId(@Param("userId") int userId);

    @Insert("INSERT INTO user_info (user_id,nickname,gender,birthday,signature) VALUES (#{userId},#{nickname},#{gender},#{birthday},#{signature})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveUserInfo(UserInfo userInfo);

    @Update("UPDATE user_info SET user_id=#{userId},nickname=#{nickname},gender=#{gender},birthday=#{birthday},signature=#{signature} WHERE id = #{id}")
    void updateUserInfo(UserInfo userInfo);

    @Delete("DELETE FROM user_info WHERE id=#{id}")
    void deleteUserInfo(@Param("id") int id);
}
