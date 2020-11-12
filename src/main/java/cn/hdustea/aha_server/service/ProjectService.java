package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ProjectInfoMapper;
import cn.hdustea.aha_server.mapper.ProjectMapper;
import cn.hdustea.aha_server.mapper.ProjectMemberMapper;
import cn.hdustea.aha_server.mapper.UserCollectionMapper;
import cn.hdustea.aha_server.vo.ProjectAndInfoBean;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
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
    private ProjectInfoMapper projectInfoMapper;
    @Resource
    private ProjectMemberMapper projectMemberMapper;
    @Resource
    private UserCollectionMapper userCollectionMapper;
    @Resource
    private UserService userService;

    /**
     * 获取所有项目粗略信息
     *
     * @return 项目粗略信息列表
     */
    public List<Project> getAllProject() {
        return projectMapper.selectAll();
    }

    /**
     * 根据项目id获取粗略信息
     *
     * @param id 项目id
     * @return 项目粗略信息
     */
    public Project getProjectById(int id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增项目
     *
     * @param project 项目粗略信息
     */
    public void saveProject(Project project) {
        projectMapper.insertSelective(project);
    }

    /**
     * 新增项目并记录作者
     *
     * @param projectAndInfoBean 项目创建信息封装
     * @param phone              手机号
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer saveProjectAndAuthor(ProjectAndInfoBean projectAndInfoBean, String phone) {
        Project project = new Project();
        ProjectInfo projectInfo = new ProjectInfo();
        BeanUtils.copyProperties(projectAndInfoBean, project);
        project.setCreatorPhone(phone);
        projectMapper.insertSelective(project);
        BeanUtils.copyProperties(projectAndInfoBean, projectInfo);
        projectInfo.setProjectId(project.getId());
        projectInfoMapper.insertSelective(projectInfo);
        return project.getId();
    }

    /**
     * 根据项目id修改项目粗略信息
     *
     * @param project   更新的项目粗略信息
     * @param projectId 项目id
     */
    public void updateProjectByProjectId(Project project, int projectId) {
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
    @CacheEvict(value = "project", key = "#id")
    public void deleteProjectById(int id) {
        projectMapper.deleteByPrimaryKey(id);
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
