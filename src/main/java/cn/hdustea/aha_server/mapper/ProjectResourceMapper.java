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

    ProjectResourceVo selectVoByPrimaryKey(Integer id);

    List<ProjectResourceVo> selectAllVoByProjectId(@Param("projectId") Integer projectId);
}