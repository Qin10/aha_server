package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.ProjectInfo;
import cn.hdustea.aha_server.entity.ProjectMember;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.mapper.ProjectInfoMapper;
import cn.hdustea.aha_server.mapper.ProjectMemberMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源详细信息服务类
 *
 * @author STEA_YY
 **/
@Service
public class ProjectInfoService {
    @Resource
    private ProjectInfoMapper projectInfoMapper;
    @Resource
    private ProjectMemberMapper projectMemberMapper;

    public ProjectInfo getProjectInfoByProjectId(int projectId) {
        return projectInfoMapper.selectByPrimaryKey(projectId);
    }

    public void updateProjectInfoByProjectId(ProjectInfo projectInfo, int projectId) {
        projectInfo.setProjectId(projectId);
        projectInfoMapper.updateByPrimaryKeySelective(projectInfo);
    }

    public List<ProjectMember> getAllProjectMemberByProjectId(int projectId) {
        return projectMemberMapper.selectAllByProjectId(projectId);
    }

    public void saveProjectMemberByProjectId(ProjectMember projectMember, int projectId) throws InsertException {
        projectMember.setProjectId(projectId);
        try {
            projectMemberMapper.insert(projectMember);
        } catch (DuplicateKeyException e) {
            throw new InsertException("该队员已经存在！");
        }
    }

    public void deleteProjectMember(int projectId, String phone) {
        projectMemberMapper.deleteByPrimaryKey(projectId, phone);
    }

    public void updateProjectMember(ProjectMember projectMember, int projectId, String phone) {
        projectMember.setProjectId(projectId);
        projectMember.setMemberPhone(phone);
        projectMemberMapper.updateByPrimaryKeySelective(projectMember);
    }
}
