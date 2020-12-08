package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.FinancialOrder;

/**
* ${description}
*
* @author STEA_YY
**/
public interface FinancialOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinancialOrder record);

    int insertSelective(FinancialOrder record);

    FinancialOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinancialOrder record);

    int updateByPrimaryKey(FinancialOrder record);
}