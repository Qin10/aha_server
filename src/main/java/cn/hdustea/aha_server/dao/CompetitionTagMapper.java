package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.CompetitionTag;
import org.springframework.stereotype.Component;

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
}