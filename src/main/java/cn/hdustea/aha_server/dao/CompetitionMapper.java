package cn.hdustea.aha_server.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.hdustea.aha_server.entity.Competition;
import org.springframework.stereotype.Component;

/**
 * 竞赛信息的数据库操作接口
 *
 * @author STEA_YY
 **/
public interface CompetitionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Competition record);

    int insertSelective(Competition record);

    Competition selectByPrimaryKey(Integer id);

    List<Competition> selectAll();

    int updateByPrimaryKeySelective(Competition record);

    int updateByPrimaryKey(Competition record);
}