package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Competition;
import cn.hdustea.aha_server.vo.CompetitionVo;

import java.util.List;

/**
 * 竞赛表mapper
 *
 * @author STEA_YY
 **/
public interface CompetitionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Competition record);

    int insertSelective(Competition record);

    Competition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Competition record);

    int updateByPrimaryKey(Competition record);

    List<Competition> selectAll();

    List<CompetitionVo> selectAllVo();

    CompetitionVo selectVoByPrimaryKey(Integer id);
}