package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Competition;import cn.hdustea.aha_server.vo.CompetitionVo;import java.util.List;

/**
 * ${description}
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
}