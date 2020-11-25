package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.vo.ProjectResourceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import cn.hdustea.aha_server.entity.ProjectResource;

/**
 * 项目资源表mapper
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

    List<ProjectResource> selectAllByProjectId(@Param("projectId") Integer projectId);

    ProjectResourceVo selectVoByPrimaryKeyAndNotFreezed(@Param("id") Integer id);

    ProjectResourceVo selectVoByPrimaryKeyAndFreezed(@Param("id") Integer id);

    List<ProjectResourceVo> selectAllVoByProjectIdAndNotFreezed(@Param("projectId") Integer projectId);

    List<ProjectResourceVo> selectAllVoByProjectIdAndFreezed(@Param("projectId") Integer projectId);

    int updatePreviewUrlById(@Param("updatedPreviewUrl") String updatedPreviewUrl, @Param("id") Integer id);

    int updateIncDownloadById(@Param("id") Integer id);

    int updateFreezedByFilename(@Param("updatedFreezed") Boolean updatedFreezed, @Param("filename") String filename);

    ProjectResource selectByFilename(@Param("filename") String filename);

}