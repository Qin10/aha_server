package cn.hdustea.aha_server.mapper;

import java.util.List;

import cn.hdustea.aha_server.entity.ProjectMember;
import cn.hdustea.aha_server.vo.ProjectMemberVo;
import org.apache.ibatis.annotations.Param;

/**
 * 项目成员表mapper
 *
 * @author STEA_YY
 **/
public interface ProjectMemberMapper {
    int deleteByPrimaryKey(@Param("projectId") Integer projectId, @Param("memberPhone") String memberPhone);

    int insert(ProjectMember record);

    int insertSelective(ProjectMember record);

    ProjectMember selectByPrimaryKey(@Param("projectId") Integer projectId, @Param("memberPhone") String memberPhone);

    int updateByPrimaryKeySelective(ProjectMember record);

    int updateByPrimaryKey(ProjectMember record);

    List<ProjectMember> selectAllByProjectId(@Param("projectId") Integer projectId);

    ProjectMember selectByProjectIdAndMemberPhoneAndEditable(@Param("projectId") Integer projectId, @Param("memberPhone") String memberPhone, @Param("editable") Boolean editable);

    ProjectMemberVo selectVoByPrimaryKey(@Param("projectId") Integer projectId, @Param("memberPhone") String memberPhone);

    List<ProjectMemberVo> selectAllVoByProjectId(@Param("projectId") Integer projectId);
}