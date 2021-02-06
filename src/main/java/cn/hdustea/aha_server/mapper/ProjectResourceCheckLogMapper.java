package cn.hdustea.aha_server.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.hdustea.aha_server.entity.ProjectResourceCheckLog;

/**
* ${description}
*
* @author STEA_YY
**/
public interface ProjectResourceCheckLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectResourceCheckLog record);

    int insertSelective(ProjectResourceCheckLog record);

    ProjectResourceCheckLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectResourceCheckLog record);

    int updateByPrimaryKey(ProjectResourceCheckLog record);

    List<ProjectResourceCheckLog> selectByResourceIdAndCheckStatus(@Param("resourceId")Integer resourceId,@Param("checkStatus")Integer checkStatus);


}