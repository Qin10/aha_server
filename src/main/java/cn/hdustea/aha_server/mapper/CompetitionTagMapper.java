package cn.hdustea.aha_server.mapper;

import java.util.List;

import cn.hdustea.aha_server.entity.CompetitionTag;

/**
 * 竞赛标签的数据库操作接口
 *
 * @author STEA_YY
 **/
public interface CompetitionTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompetitionTag record);

    int insertSelective(CompetitionTag record);

    CompetitionTag selectByPrimaryKey(Integer id);

    List<CompetitionTag> selectAll();

    int updateByPrimaryKeySelective(CompetitionTag record);

    int updateByPrimaryKey(CompetitionTag record);
}