package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.dto.ResourceAvgScoreDto;
import cn.hdustea.aha_server.entity.ProjectResourceScore;
import cn.hdustea.aha_server.vo.ProjectResourceScoreVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ProjectResourceScoreMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(ProjectResourceScore record);

    int insertSelective(ProjectResourceScore record);

    ProjectResourceScore selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(ProjectResourceScore record);

    int updateByPrimaryKey(ProjectResourceScore record);

    List<ProjectResourceScore> selectAllByResourceId(@Param("resourceId") Integer resourceId);

    List<ProjectResourceScore> selectAllByUserId(@Param("userId") Integer userId);

    List<ResourceAvgScoreDto> selectAllAvgScore();

    Integer countByResourceId(@Param("resourceId") Integer resourceId);

    List<ProjectResourceScoreVo> selectAllVoByResourceId(@Param("resourceId") Integer resourceId);
}