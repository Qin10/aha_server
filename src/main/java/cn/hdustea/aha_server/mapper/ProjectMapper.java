package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.vo.ProjectDetailVo;
import cn.hdustea.aha_server.vo.ProjectRoughVo;
import cn.hdustea.aha_server.entity.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    List<Project> selectAll();

    List<ProjectRoughVo> selectAllRough();

    ProjectRoughVo selectRoughByPrimaryKey(Integer id);

    ProjectDetailVo selectDetailByPrimaryKey(Integer id);

    int updateReadById(@Param("updatedRead") Integer updatedRead, @Param("id") Integer id);

    List<ProjectRoughVo> selectAllRoughByConditions(@Param("phone") String phone, @Param("compId") Integer compId, @Param("awardLevel") Integer awardLevel, @Param("sortBy") String sortBy, @Param("orderBy") String orderBy, @Param("passed") Boolean passed);

    int updateIncCollectById(@Param("id") Integer id);

    int updateDecCollectById(@Param("id") Integer id);
}