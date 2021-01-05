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

    ProjectResourceVo selectVoByPrimaryKey(Integer id);

    List<ProjectResource> selectAllByProjectId(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectIdAndPassed(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectIdAndNotPassed(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectId(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectIdAndNotFreezed(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectIdAndFreezed(@Param("projectId") Integer projectId);

    int updatePreviewUrlById(@Param("updatedPreviewUrl") String updatedPreviewUrl, @Param("id") Integer id);

    int updateIncDownloadById(@Param("id") Integer id);

    int updateFreezedByFilename(@Param("updatedFreezed") Boolean updatedFreezed, @Param("filename") String filename);

    ProjectResource selectByFilename(@Param("filename") String filename);

    int updatePassedByProjectId(@Param("updatedPassed") Boolean updatedPassed, @Param("projectId") Integer projectId);

    int updatePassedById(@Param("updatedPassed") Boolean updatedPassed, @Param("id") Integer id);

    List<ProjectResourceVo> selectAllVoByConditions(@Param("passed") Boolean passed,@Param("projectId") Integer projectId);
}