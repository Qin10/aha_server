package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.OssPolicyBean;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.ProjectInfoService;
import cn.hdustea.aha_server.service.ProjectResourceService;
import cn.hdustea.aha_server.service.ProjectService;
import cn.hdustea.aha_server.util.RedisUtil;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import cn.hdustea.aha_server.vo.UrlBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 项目相关请求
 *
 * @author STEA_YY
 **/
@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectController {
    protected static final String MODULE_NAME = "项目模块";
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectInfoService projectInfoService;
    @Resource
    private ProjectResourceService projectResourceService;
    @Resource
    private OssService ossService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserOperationLogConfig userOperationLogConfig;

    /**
     * 获取全部项目粗略信息
     */
    @RequiresLogin
    @GetMapping
    public ResponseBean<List<Project>> getAllProject() {
        List<Project> projects = projectService.getAllProject();
        return new ResponseBean<>(200, "succ", projects, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 根据项目id获取项目粗略信息
     *
     * @param projectId 项目id
     */
    @RequiresLogin
    @GetMapping("/{projectId}")
    public ResponseBean<Project> getProjectById(@PathVariable("projectId") int projectId) {
        Project project = projectService.getProjectById(projectId);
        return new ResponseBean<>(200, "succ", project, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 获取oss公开资源上传签名(用于上传项目头像)
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/sign/upload/public")
    public ResponseBean<OssPolicyBean> signUploadPublicFile() {
        String phone = ThreadLocalUtil.getCurrentUser();
        OssPolicyBean ossPolicyBean = ossService.signUpload("resource_avatar/" + phone, false);
        return new ResponseBean<>(200, "succ", ossPolicyBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 新增项目
     *
     * @param project 项目粗略信息
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping()
    public ResponseBean<Object> saveProject(@RequestBody @Validated Project project) {
        String phone = ThreadLocalUtil.getCurrentUser();
        projectService.saveProjectAndAuthor(project, phone);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 修改项目粗略信息
     *
     * @param project   修改的项目粗略信息
     * @param projectId 项目id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/{projectId}")
    public ResponseBean<Object> updateProjectById(@RequestBody Project project, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.updateProjectByProjectId(project, projectId);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 删除项目
     *
     * @param projectId 项目id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @DeleteMapping("/{projectId}")
    public ResponseBean<Object> deleteProjectById(@PathVariable("projectId") int projectId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.deleteProjectById(projectId);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 根据项目id获取项目详细信息
     *
     * @param projectId 项目id
     */
    @RequiresLogin
    @GetMapping("/{projectId}/info")
    public ResponseBean<ProjectInfo> getProjectInfoById(@PathVariable("projectId") int projectId) {
        ProjectInfo projectInfo = projectInfoService.getProjectInfoByProjectId(projectId);
        if (projectInfo != null) {
            addReadByProjectId(projectId);
            log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "查看项目", "id=" + projectId);
        }
        return new ResponseBean<>(200, "succ", projectInfo, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 修改项目详细信息
     *
     * @param projectInfo 修改的项目详细信息
     * @param projectId   项目id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin
    @PutMapping("/info/{projectId}")
    public ResponseBean<Object> updateProjectInfoById(@RequestBody ProjectInfo projectInfo, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectInfoService.updateProjectInfoByProjectId(projectInfo, projectId);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 根据项目id获取所有项目成员
     *
     * @param projectId 项目id
     */
    @RequiresLogin()
    @GetMapping("/{projectId}/member")
    public ResponseBean<List<ProjectMember>> getAllProjectMemberByProjectId(@PathVariable("projectId") int projectId) {
        List<ProjectMember> projectMembers = projectInfoService.getAllProjectMemberByProjectId(projectId);
        return new ResponseBean<>(200, "succ", projectMembers, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 新增项目成员
     *
     * @param projectMember 项目成员
     * @param projectId     项目id
     * @throws PermissionDeniedException 无操作权限异常
     * @throws InsertException           插入异常
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping("/member/{projectId}")
    public ResponseBean<Object> saveProjectMemberById(@RequestBody @Validated ProjectMember projectMember, @PathVariable("projectId") int projectId) throws PermissionDeniedException, InsertException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectInfoService.saveProjectMemberByProjectId(projectMember, projectId);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 修改项目成员信息
     *
     * @param projectMember 修改的项目成员
     * @param projectId     项目id
     * @param memberPhone   成员手机号
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/member/{projectId}/{memberPhone}")
    public ResponseBean<Object> updateResourceMemberById(@RequestBody ProjectMember projectMember, @PathVariable("projectId") int projectId, @PathVariable("memberPhone") String memberPhone) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectInfoService.updateProjectMember(projectMember, projectId, memberPhone);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 删除项目成员
     *
     * @param projectId   项目id
     * @param memberPhone 成员手机号
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin
    @DeleteMapping("/member/{projectId}/{memberPhone}")
    public ResponseBean<Object> deleteProjectMember(@PathVariable("projectId") int projectId, @PathVariable("memberPhone") String memberPhone) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectInfoService.deleteProjectMember(projectId, memberPhone);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 根据项目id获取所有项目资源
     *
     * @param projectId 项目id
     */
    @RequiresLogin()
    @GetMapping("/{projectId}/resource")
    public ResponseBean<List<ProjectResource>> getAllProjectResourceByProjectId(@PathVariable("projectId") int projectId) {
        List<ProjectResource> projectResources = projectResourceService.getAllProjectResourceByProjectId(projectId);
        return new ResponseBean<>(200, "succ", projectResources, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 获取oss私有资源上传签名(用于上传资源文件)
     *
     * @param projectId 项目id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/{projectId}/resource/sign/upload/private")
    public ResponseBean<OssPolicyBean> signUploadPrivateFile(@PathVariable("projectId") int projectId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        Project project = projectService.getProjectById(projectId);
        OssPolicyBean ossPolicyBean = ossService.signUpload(project.getName() + "/", true);
        return new ResponseBean<>(200, "succ", ossPolicyBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 新增项目资源
     *
     * @param projectResource 项目资源
     * @param projectId       项目id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping("/resource/{projectId}")
    public ResponseBean<Object> saveProjectResourceByProjectId(@RequestBody @Validated ProjectResource projectResource, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectResourceService.saveProjectResourceByProjectId(projectResource, projectId);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 修改项目资源
     *
     * @param projectResource   修改的项目资源
     * @param projectResourceId 项目资源id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/resource/{projectResourceId}")
    public ResponseBean<Object> updateProjectResourceById(@RequestBody ProjectResource projectResource, @PathVariable("projectResourceId") int projectResourceId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        ProjectResource possibleProjectResource = projectResourceService.getProjectResourceById(projectResourceId);
        if (possibleProjectResource == null || !projectService.hasPermission(phone, possibleProjectResource.getProjectId())) {
            throw new PermissionDeniedException();
        }
        projectResourceService.updateProjectResourceById(projectResource, projectResourceId);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 删除项目资源
     *
     * @param projectResourceId 项目资源id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @DeleteMapping("/resource/{projectResourceId}")
    public ResponseBean<Object> deleteProjectResourceById(@PathVariable("projectResourceId") int projectResourceId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        ProjectResource possibleProjectResource = projectResourceService.getProjectResourceById(projectResourceId);
        if (possibleProjectResource == null || !projectService.hasPermission(phone, possibleProjectResource.getProjectId())) {
            throw new PermissionDeniedException();
        }
        projectResourceService.deleteProjectResourceById(projectResourceId);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 获取项目资源文件oss下载签名
     *
     * @param projectResourceId 项目资源id
     * @throws SelectException 资源不存在异常
     */
    @RequiresLogin
    @GetMapping("/resource/{projectResourceId}/sign/download")
    public ResponseBean<UrlBean> signDownloadResourceByid(@PathVariable("projectResourceId") int projectResourceId) throws SelectException {
        String url = projectResourceService.signDownloadProjectResourceByid(projectResourceId);
        UrlBean urlBean = new UrlBean();
        urlBean.setUrl(url);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "下载资源", "id=" + projectResourceId);
        return new ResponseBean<>(200, "succ", urlBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 获取用户收藏项目列表
     *
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @GetMapping("/collection")
    public ResponseBean<List<UserCollection>> getAllCollection() throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        List<UserCollection> collections = projectService.getAllCollectionByPhone(phone);
        return new ResponseBean<>(200, "succ", collections, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 收藏项目
     *
     * @param projectId 项目id
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @PostMapping("/collection/{projectId}")
    public ResponseBean<Object> collectResource(@PathVariable("projectId") int projectId) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        projectService.saveCollection(projectId, phone);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "收藏资源", "id=" + projectId);
        return new ResponseBean<>(200, "收藏成功！", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 取消收藏
     *
     * @param projectId 项目id
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @DeleteMapping("/collection/{projectId}")
    public ResponseBean<Object> cancelCollection(@PathVariable("projectId") int projectId) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        projectService.deleteCollection(projectId, phone);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "取消收藏资源", "id=" + projectId);
        return new ResponseBean<>(200, "取消收藏成功！", null, TimeUtil.getFormattedTime(new Date()));
    }

    private void addReadByProjectId(int projectId) {
        redisUtil.hincr(RedisUtil.PROJECT_READ_KEY, Integer.toString(projectId), 1);
    }
}
