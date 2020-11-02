package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.CompetitionTag;import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface CompetitionTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionTag record);

    int insertSelective(CompetitionTag record);

    CompetitionTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompetitionTag record);

    int updateByPrimaryKey(CompetitionTag record);

    List<CompetitionTag> selectAll();
}