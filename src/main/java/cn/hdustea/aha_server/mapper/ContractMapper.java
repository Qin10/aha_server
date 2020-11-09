package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Contract;

/**
 * 合同表mapper
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