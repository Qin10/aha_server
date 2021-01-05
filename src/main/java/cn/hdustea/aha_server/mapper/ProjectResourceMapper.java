package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.ProjectResource;
import cn.hdustea.aha_server.vo.ProjectResourceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    ProjectResourceVo selectVoByPrimaryKeyAndPassed(@Param("id") Integer id, @Param("passed") Boolean passed);

    List<ProjectResource> selectAllByProjectId(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectIdAndPassed(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectId(@Param("projectId") Integer projectId);

    int updatePreviewUrlById(@Param("updatedPreviewUrl") String updatedPreviewUrl, @Param("id") Integer id);

    int updateIncDownloadById(@Param("id") Integer id);

    int updatePassedByProjectId(@Param("updatedPassed") Boolean updatedPassed, @Param("projectId") Integer projectId);

    int updatePassedById(@Param("updatedPassed") Boolean updatedPassed, @Param("id") Integer id);

    List<ProjectResourceVo> selectAllVoByConditions(@Param("resourcePassed") Boolean resourcePassed, @Param("projectPassed") Boolean projectPassed, @Param("projectId") Integer projectId);
}