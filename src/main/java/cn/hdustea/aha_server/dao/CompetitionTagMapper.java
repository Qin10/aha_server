package cn.hdustea.aha_server.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.hdustea.aha_server.entity.CompetitionTag;
import org.springframework.stereotype.Component;

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