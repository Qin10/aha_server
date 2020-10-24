package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.Competition;
import org.springframework.stereotype.Component;

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
}