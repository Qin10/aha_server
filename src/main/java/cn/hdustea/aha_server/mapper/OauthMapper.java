package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Oauth;
import cn.hdustea.aha_server.vo.OauthVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface OauthMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("oauthType") String oauthType);

    int insert(Oauth record);

    int insertSelective(Oauth record);

    Oauth selectByPrimaryKey(@Param("userId") Integer userId, @Param("oauthType") String oauthType);

    int updateByPrimaryKeySelective(Oauth record);

    int updateByPrimaryKey(Oauth record);

    int deleteByPrimaryKey(Integer id);

    Oauth selectByPrimaryKey(Integer id);

    Oauth selectByOauthTypeAndOauthId(@Param("oauthType") String oauthType, @Param("oauthId") String oauthId);

    List<OauthVo> selectAllVoByUserId(Integer userId);
}