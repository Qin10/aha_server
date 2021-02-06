package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.RewardFinancialScheme;

/**
* ${description}
*
* @author STEA_YY
**/
public interface RewardFinancialSchemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RewardFinancialScheme record);

    int insertSelective(RewardFinancialScheme record);

    RewardFinancialScheme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RewardFinancialScheme record);

    int updateByPrimaryKey(RewardFinancialScheme record);
}