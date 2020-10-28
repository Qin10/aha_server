package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.Contract;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ContractMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);
}