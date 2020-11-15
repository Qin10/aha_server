package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.vo.ProjectDetailVo;
import cn.hdustea.aha_server.vo.ProjectRoughVo;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ProjectMapper;
import cn.hdustea.aha_server.mapper.ProjectMemberMapper;
import cn.hdustea.aha_server.mapper.UserCollectionMapper;
import cn.hdustea.aha_server.dto.ProjectDto;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 项目服务类
 *
 * @author STEA_YY
 **/
@Service
public class ProjectService {
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ProjectMemberMapper projectMemberMapper;
    @Resource
    private UserCollectionMapper userCollectionMapper;
    @Resource
    private UserService userService;

    /**
     * 获取所有项目信息
     *
     * @return 项目信息列表
     */
    public List<Project> getAllProject() {
        return projectMapper.selectAll();
    }

    /**
     * 获取所有项目粗略信息
     *
     * @return 项目粗略信息列表
     */
    public List<ProjectRoughVo> getAllProjectRoughInfo() {
        return projectMapper.selectAllRough();
    }

    /**
     * 根据项目id获取详细信息
     *
     * @param id 项目id
     * @return 项目信息
     */
    public ProjectDetailVo getProjectDetailById(int id) {
        return projectMapper.selectDetailByPrimaryKey(id);
    }

    /**
     * 新增项目
     *
     * @param project 项目信息
     */
    public void saveProject(Project project) {
        projectMapper.insertSelective(project);
    }

    /**
     * 新增项目并记录作者
     *
     * @param projectDto 项目信息
     * @param phone   手机号
     */
    public Integer saveProjectAndAuthor(ProjectDto projectDto, String phone) {
        Project project = new Project();
        BeanUtils.copyProperties(projectDto,project);
        project.setCreatorPhone(phone);
        projectMapper.insertSelective(project);
        return project.getId();
    }

    /**
     * 根据项目id修改项目信息
     *
     * @param projectDto   更新的项目信息
     * @param projectId 项目id
     */
    public void updateProjectByProjectId(ProjectDto projectDto, int projectId) {
        Project project = new Project();
        BeanUtils.copyProperties(projectDto,project);
        project.setId(projectId);
        projectMapper.updateByPrimaryKeySelective(project);
    }

    /**
     * 检查用户是否有项目修改权限
     *
     * @param phone 手机号
     * @param id    项目id
     * @return 是否有修改权限
     */
    public boolean hasPermission(String phone, int id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project != null && project.getCreatorPhone().equals(phone)) {
            return true;
        } else {
            ProjectMember projectMember = projectMemberMapper.selectByProjectIdAndMemberPhoneAndEditable(id, phone, true);
            return projectMember != null;
        }
    }

    /**
     * 删除项目
     *
     * @param id 项目id
     */
    public void deleteProjectById(int id) {
        projectMapper.deleteByPrimaryKey(id);
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

    @Transactional(rollbackFor = {Exception.class})
    public void updateProjectMembers(List<ProjectMember> projectMembers, int projectId) {
        for (ProjectMember projectMember : projectMembers) {
            projectMember.setProjectId(projectId);
            projectMemberMapper.updateByPrimaryKeySelective(projectMember);
        }
    }

    /**
     * 获取全部用户收藏项目
     *
     * @param phone 手机号
     * @return 用户收藏列表
     * @throws SelectException 用户不存在异常
     */
    public List<UserCollection> getAllCollectionByPhone(String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        return userCollectionMapper.selectAllByUserId(user.getId());
    }

    /**
     * 收藏项目
     *
     * @param projectId 项目id
     * @param phone     手机号
     * @throws SelectException 用户不存在异常
     */
    public void saveCollection(int projectId, String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        UserCollection userCollection = new UserCollection();
        userCollection.setProjectId(projectId);
        userCollection.setUserId(user.getId());
        userCollection.setTimestamp(new Date());
        try {
            userCollectionMapper.insert(userCollection);
        } catch (DuplicateKeyException ignored) {
        }
    }

    /**
     * 取消收藏
     *
     * @param projectId 项目id
     * @param phone     手机号
     * @throws SelectException 用户不存在异常
     */
    public void deleteCollection(int projectId, String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        userCollectionMapper.deleteByPrimaryKey(user.getId(), projectId);
    }
}
