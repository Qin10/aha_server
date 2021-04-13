package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.entity.UserStatistics;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import cn.hdustea.aha_server.vo.UserTalentMarketDetailVo;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserRoughInfoVo selectRoughVoByPrimaryKey(Integer userId);

    List<UserRoughInfoVo> selectAllTalentMarketRoughVo();

    List<UserContribPointVo> selectAllTalentMarketRoughVoOrderByContrib();

    UserTalentMarketDetailVo selectTalentMarketDetailVoByPrimaryKey(Integer userId);
}