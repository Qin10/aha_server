package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.CompetitionType;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface CompetitionTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionType record);

    int insertSelective(CompetitionType record);

    CompetitionType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionType record);

    int updateByPrimaryKey(CompetitionType record);

    List<CompetitionType> selectAll();
}