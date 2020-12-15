package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.ProjectMember;
import cn.hdustea.aha_server.vo.ProjectMemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ProjectMemberMapper {
    int deleteByPrimaryKey(@Param("projectId") Integer projectId, @Param("memberUserId") Integer memberUserId);

    int insert(ProjectMember record);

    int insertSelective(ProjectMember record);

    ProjectMember selectByPrimaryKey(@Param("projectId") Integer projectId, @Param("memberUserId") Integer memberUserId);

    int updateByPrimaryKeySelective(ProjectMember record);

    int updateByPrimaryKey(ProjectMember record);

    List<ProjectMember> selectAllByProjectId(@Param("projectId") Integer projectId);

    ProjectMember selectByProjectIdAndMemberUserIdAndEditable(@Param("projectId") Integer projectId, @Param("memberUserId") Integer memberUserId, @Param("editable") Boolean editable);

    List<ProjectMemberVo> selectAllVoByProjectIdOrderByRank(@Param("projectId") Integer projectId);
}