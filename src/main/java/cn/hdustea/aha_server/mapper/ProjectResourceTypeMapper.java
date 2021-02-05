package cn.hdustea.aha_server.mapper;
import cn.hdustea.aha_server.entity.ProjectResourceType;

import java.util.List;

/**
* ${description}
*
* @author STEA_YY
**/
public interface ProjectResourceTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectResourceType record);

    int insertSelective(ProjectResourceType record);

    ProjectResourceType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectResourceType record);

    int updateByPrimaryKey(ProjectResourceType record);

    List<ProjectResourceType> selectAll();


}