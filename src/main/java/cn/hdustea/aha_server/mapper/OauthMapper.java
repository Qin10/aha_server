package cn.hdustea.aha_server.mapper;

import java.util.List;

import cn.hdustea.aha_server.entity.Oauth;
import org.apache.ibatis.annotations.Param;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface OauthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Oauth record);

    int insertSelective(Oauth record);

    Oauth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Oauth record);

    int updateByPrimaryKey(Oauth record);

    Oauth selectByOauthTypeAndOauthId(@Param("oauthType") String oauthType, @Param("oauthId") String oauthId);

}