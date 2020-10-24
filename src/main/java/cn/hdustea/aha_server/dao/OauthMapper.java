package cn.hdustea.aha_server.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.hdustea.aha_server.entity.Oauth;

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

    Oauth selectByOauthTypeAndOauthId(@Param("oauthType") String oauthType, @Param("oauthId") String oauthId);

    int updateByPrimaryKeySelective(Oauth record);

    int updateByPrimaryKey(Oauth record);
}