package cn.hdustea.aha_server.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.hdustea.aha_server.entity.Project;

/**
* 项目表mapper
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

    int updateReadById(@Param("updatedRead")Integer updatedRead,@Param("id")Integer id);



}