package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.ProjectInfo;
import cn.hdustea.aha_server.entity.ProjectMember;
import cn.hdustea.aha_server.entity.ProjectResource;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.mapper.ProjectInfoMapper;
import cn.hdustea.aha_server.mapper.ProjectMemberMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 根据项目id获取项目详细信息
     *
     * @param projectId 项目id
     * @return 项目详细信息
     */
    public ProjectInfo getProjectInfoByProjectId(int projectId) {
        return projectInfoMapper.selectByPrimaryKey(projectId);
    }

    /**
     * 根据项目id更新项目详细信息
     *
     * @param projectInfo 更新的项目详细信息
     * @param projectId   项目id
     */
    public void updateProjectInfoByProjectId(ProjectInfo projectInfo, int projectId) {
        projectInfo.setProjectId(projectId);
        projectInfoMapper.updateByPrimaryKeySelective(projectInfo);
    }

    /**
     * 根据项目id添加项目成员
     *
     * @param projectMember 项目成员
     * @param projectId     项目id
     * @throws InsertException 插入异常
     */
    public void saveProjectMemberByProjectId(ProjectMember projectMember, int projectId) throws InsertException {
        projectMember.setProjectId(projectId);
        try {
            projectMemberMapper.insert(projectMember);
        } catch (DuplicateKeyException e) {
            throw new InsertException("该队员已经存在！");
        }
    }

    /**
     * 根据项目id和手机号删除项目成员
     *
     * @param projectId 项目id
     * @param phone     手机号
     */
    public void deleteProjectMember(int projectId, String phone) {
        projectMemberMapper.deleteByPrimaryKey(projectId, phone);
    }

    /**
     * 根据项目id和手机号更新项目成员
     *
     * @param projectMember 更新的项目成员
     * @param projectId     项目id
     * @param phone         手机号
     */
    public void updateProjectMember(ProjectMember projectMember, int projectId, String phone) {
        projectMember.setProjectId(projectId);
        projectMember.setMemberPhone(phone);
        projectMemberMapper.updateByPrimaryKeySelective(projectMember);
    }
}
