package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.ProjectInfo;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface ProjectInfoMapper {
    int deleteByPrimaryKey(Integer projectId);

    int insert(ProjectInfo record);

    int insertSelective(ProjectInfo record);

    ProjectInfo selectByPrimaryKey(Integer projectId);

    int updateByPrimaryKeySelective(ProjectInfo record);

    int updateByPrimaryKey(ProjectInfo record);
}