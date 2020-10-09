package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.Oauth;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OauthDao {
    @Select("SELECT * FROM oauth WHERE oauth_type = #{oauthType} AND oauth_id = #{oauthId}")
    @Results({@Result(property = "user", column = "user_id", one = @One(select = "cn.hdustea.aha_server.dao.UserDao.findUserById", fetchType = FetchType.EAGER))})
    Oauth findOauthByOauthTypeAndOauthId(String oauthType, String oauthId);
}
