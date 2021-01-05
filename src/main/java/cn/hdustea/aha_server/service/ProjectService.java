package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.ProjectCheckDto;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.ProjectDetailVo;
import cn.hdustea.aha_server.vo.ProjectRoughVo;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ProjectMapper;
import cn.hdustea.aha_server.mapper.ProjectMemberMapper;
import cn.hdustea.aha_server.mapper.UserCollectionMapper;
import cn.hdustea.aha_server.dto.ProjectDto;
import cn.hdustea.aha_server.vo.UserCollectionVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
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
    private ProjectResourceService projectResourceService;
    @Resource
    private UserCollectionMapper userCollectionMapper;
    @Resource
    private UserService userService;

    /**
     * 分页获取所有项目粗略信息
     *
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @param userId     所有者用户id
     * @param compId     竞赛id
     * @param awardLevel 获奖级别
     * @param sortBy     排序依据
     * @param orderBy    排序方式
     * @param passed     是否通过
     * @return 项目列表
     * @throws SelectException 查询异常
     */
    public PageVo<List<ProjectRoughVo>> getAllProjectRoughInfoPagable(int pageNum, int pageSize, Integer userId, Integer compId, Integer awardLevel, String sortBy, String orderBy, Boolean passed) throws SelectException {
        List<ProjectRoughVo> projectRoughVos;
        String currentSortBy = "p_id";
        String currentOrderBy = "desc";
        if (sortBy != null && !sortBy.equals("")) {
            switch (sortBy) {
                case "time": {
                    currentSortBy = "p_id";
                    break;
                }
                case "collect": {
                    currentSortBy = "p_collect";
                    break;
                }
                case "read": {
                    currentSortBy = "p_read";
                    break;
                }
                case "awardLevel": {
                    currentSortBy = "p_award_level";
                    break;
                }
                default: {
                    throw new SelectException("'sortBy'参数取值错误！");
                }
            }
        }
        if (orderBy != null && !orderBy.equals("")) {
            switch (orderBy) {
                case "desc": {
                    currentOrderBy = "desc";
                    break;
                }
                case "asc": {
                    currentOrderBy = "asc";
                    break;
                }
                default: {
                    throw new SelectException("'orderBy'参数取值错误！");
                }
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy(currentSortBy + " " + currentOrderBy);
        projectRoughVos = projectMapper.selectAllRoughByConditions(userId, compId, awardLevel, passed);
        PageInfo<ProjectRoughVo> pageInfo = new PageInfo<>(projectRoughVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    /**
     * 根据项目id获取详细信息
     *
     * @param id 项目id
     * @return 项目信息
     * @throws SelectException 项目不存在异常
     */
    public ProjectDetailVo getProjectDetailById(int id) throws SelectException {
        ProjectDetailVo projectDetailVo = projectMapper.selectDetailByPrimaryKey(id);
        if (projectDetailVo == null) {
            throw new SelectException("项目不存在！");
        }
        return projectDetailVo;
    }

    public Project getProjectById(int id) throws SelectException {
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project == null) {
            throw new SelectException("项目不存在！");
        }
        return project;
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
     * @param userId     用户id
     */
    public Integer saveProjectAndAuthor(ProjectDto projectDto, int userId) {
        Project project = new Project();
        BeanUtils.copyProperties(projectDto, project);
        project.setCreatorUserId(userId);
        projectMapper.insertSelective(project);
        return project.getId();
    }

    /**
     * 根据项目id修改项目信息
     *
     * @param projectDto 更新的项目信息
     * @param projectId  项目id
     */
    public void updateProjectByProjectId(ProjectDto projectDto, int projectId) {
        Project project = new Project();
        BeanUtils.copyProperties(projectDto, project);
        project.setId(projectId);
        projectMapper.updateByPrimaryKeySelective(project);
    }

    /**
     * 检查用户是否有项目修改权限
     *
     * @param userId 用户id
     * @param id     项目id
     * @return 是否有修改权限
     */
    public boolean hasPermission(int userId, int id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project != null && project.getCreatorUserId().equals(userId)) {
            return true;
        } else {
            ProjectMember projectMember = projectMemberMapper.selectByProjectIdAndMemberUserIdAndEditable(id, userId, true);
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
    public void saveProjectMemberByProjectId(ProjectMember projectMember, int projectId) throws InsertException, SelectException {
        userService.getExistUserVoById(projectMember.getMemberUserId());
        projectMember.setProjectId(projectId);
        try {
            projectMemberMapper.insert(projectMember);
        } catch (DuplicateKeyException e) {
            throw new InsertException("该队员已经存在！");
        }
    }

    /**
     * 根据项目id和用户id删除项目成员
     *
     * @param projectId 项目id
     * @param userId    用户id
     */
    public void deleteProjectMember(int projectId, int userId) {
        projectMemberMapper.deleteByPrimaryKey(projectId, userId);
    }

    /**
     * 根据项目id和用户id更新项目成员
     *
     * @param projectMember 更新的项目成员
     * @param projectId     项目id
     * @param userId        用户id
     */
    public void updateProjectMember(ProjectMember projectMember, int projectId, int userId) {
        projectMember.setProjectId(projectId);
        projectMember.setMemberUserId(userId);
        projectMemberMapper.updateByPrimaryKeySelective(projectMember);
    }

    /**
     * 批量更新项目成员
     *
     * @param projectMembers 更新的项目多个成员
     * @param projectId      项目id
     */
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
     * @param userId 用户id
     * @return 用户收藏列表
     */
    public List<UserCollectionVo> getAllCollectionByUserId(int userId) {
        return userCollectionMapper.selectAllVoByUserId(userId);
    }

    /**
     * 收藏项目
     *
     * @param projectId 项目id
     * @param userId    用户id
     */
    public void saveCollection(int projectId, int userId) throws InsertException {
        UserCollection userCollection = new UserCollection();
        userCollection.setProjectId(projectId);
        userCollection.setUserId(userId);
        userCollection.setTimestamp(new Date());
        try {
            userCollectionMapper.insert(userCollection);
        } catch (DuplicateKeyException e) {
            throw new InsertException("您已收藏过该项目！");
        } catch (DataIntegrityViolationException e) {
            throw new InsertException("项目不存在！");
        }
    }

    /**
     * 取消收藏
     *
     * @param projectId 项目id
     * @param userId    用户id
     */
    public void deleteCollection(int projectId, int userId) throws DeleteException {
        int result = userCollectionMapper.deleteByPrimaryKey(userId, projectId);
        if (result == 0) {
            throw new DeleteException("您未收藏过此项目！");
        }
    }

    /**
     * 判断项目是否被收藏
     *
     * @param projectId 项目id
     * @param userId    用户id
     * @return 项目是否被收藏
     */
    public boolean hasCollected(int projectId, int userId) {
        UserCollection userCollection = userCollectionMapper.selectByPrimaryKey(userId, projectId);
        return userCollection != null;
    }

    public boolean isMember(int projectId, int userId) {
        Project project = projectMapper.selectByPrimaryKey(projectId);
        if (project != null && project.getCreatorUserId().equals(userId)) {
            return true;
        } else {
            ProjectMember projectMember = projectMemberMapper.selectByPrimaryKey(projectId, userId);
            return projectMember != null;
        }
    }

    /**
     * 项目收藏数量递增
     *
     * @param projectId 项目id
     */
    public void incrCollectByProjectId(int projectId) {
        projectMapper.updateIncCollectById(projectId);
    }

    /**
     * 项目收藏数量递减
     *
     * @param projectId 项目id
     */
    public void descCollectByProjectId(int projectId) {
        projectMapper.updateDecCollectById(projectId);
    }

    /**
     * 处理审核项目请求
     *
     * @param projectCheckDto 项目审核信息
     * @param projectId       项目id
     * @throws UpdateException 更新异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void checkProjectByProjectId(ProjectCheckDto projectCheckDto, int projectId) throws UpdateException {
        if (projectMapper.selectByPrimaryKey(projectId) == null) {
            throw new UpdateException("项目不存在！");
        }
        Project project = new Project();
        BeanUtils.copyProperties(projectCheckDto, project);
        projectMapper.updateByPrimaryKeySelective(project);
        projectResourceService.getAllProjectResourceVoByConditions(null, null, projectId);
        projectResourceService.updatePassedByProjectAndConvertDocument(projectCheckDto.getPassed(), projectId);
    }

    public boolean isPassed(int projectId) {
        Project project = projectMapper.selectByPrimaryKey(projectId);
        if (project == null) {
            return false;
        } else {
            return project.getPassed();
        }
    }
}