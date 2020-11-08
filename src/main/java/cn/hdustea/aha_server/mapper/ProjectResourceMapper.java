package cn.hdustea.aha_server.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.hdustea.aha_server.entity.ProjectResource;

/**
* ${description}
*
* @author STEA_YY
**/
public interface ProjectResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectResource record);

    int insertSelective(ProjectResource record);

    ProjectResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectResource record);

    int updateByPrimaryKey(ProjectResource record);

    List<ProjectResource> selectAllByProjectId(@Param("projectId")Integer projectId);
}