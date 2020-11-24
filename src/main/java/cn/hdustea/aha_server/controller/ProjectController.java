package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.ProjectResourceDto;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.vo.*;
import cn.hdustea.aha_server.dto.ProjectDto;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.ProjectResourceService;
import cn.hdustea.aha_server.service.ProjectService;
import cn.hdustea.aha_server.util.RedisUtil;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目相关请求
 *
 * @author STEA_YY
 **/
@Slf4j(topic = "userOperationLog")
@RestController
@RequestMapping("/project")
public class ProjectController {
    protected static final String MODULE_NAME = "项目模块";
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectResourceService projectResourceService;
    @Resource
    private OssService ossService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserOperationLogConfig userOperationLogConfig;

//    /**
//     * 获取全部项目粗略信息
//     */
//    @RequiresLogin
//    @GetMapping
//    public ResponseBean<List<ProjectRoughVo>> getAllProject() {
//        List<ProjectRoughVo> projectRoughVos = projectService.getAllProjectRoughInfo();
//        return new ResponseBean<>(200, "succ", projectRoughVos);
//    }

    /**
     * 分页获取所有项目粗略信息
     *
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @param phone      手机号
     * @param compId     竞赛id
     * @param awardLevel 获奖级别
     * @param sortBy     排序关键字
     * @param orderBy    排序方式
     */
    @RequiresLogin
    @GetMapping
    public ResponseBean<PageVo<List<ProjectRoughVo>>> getAllProjectPageable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "compId", required = false) Integer compId, @RequestParam(value = "awardLevel", required = false) Integer awardLevel, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "orderBy", required = false) String orderBy) {
        Boolean passed = null;
        PageVo<List<ProjectRoughVo>> projectRoughVos = projectService.getAllProjectRoughInfoPagable(pageNum, pageSize, phone, compId, awardLevel, sortBy, orderBy, passed);
        return new ResponseBean<>(200, "succ", projectRoughVos);
    }

    /**
     * 根据项目id获取项目详细信息
     *
     * @param projectId 项目id
     * @throws SelectException 项目不存在异常
     */
    @RequiresLogin
    @GetMapping("/{projectId}")
    public ResponseBean<ProjectDetailVo> getProjectById(@PathVariable("projectId") int projectId) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        ProjectDetailVo projectDetailVo = projectService.getProjectDetailById(projectId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "查看项目", "id=" + projectId);
        incrReadByProjectId(projectId, phone);
        return new ResponseBean<>(200, "succ", projectDetailVo);
    }

    /**
     * 获取oss公开资源上传签名(用于上传项目头像和获奖证明材料)
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/sign/upload/public")
    public ResponseBean<OssPolicyVo> signUploadPublicFile() {
        String phone = ThreadLocalUtil.getCurrentUser();
        OssPolicyVo ossPolicyVo = ossService.signUpload("resource/" + phone, false);
        return new ResponseBean<>(200, "succ", ossPolicyVo);
    }

    /**
     * 新增项目
     *
     * @param projectDto 项目信息
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping()
    public ResponseBean<InsertedIdVo> saveProject(@RequestBody @Validated ProjectDto projectDto) {
        String phone = ThreadLocalUtil.getCurrentUser();
        Integer projectId = projectService.saveProjectAndAuthor(projectDto, phone);
        return new ResponseBean<>(200, "succ", new InsertedIdVo(projectId));
    }

    /**
     * 修改项目信息
     *
     * @param projectDto 修改的项目信息
     * @param projectId  项目id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/{projectId}")
    public ResponseBean<Object> updateProjectById(@RequestBody ProjectDto projectDto, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.updateProjectByProjectId(projectDto, projectId);
        return new ResponseBean<>(200, "succ", null);
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
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据项目id获取所有项目成员
     *
     * @param projectId 项目id
     * @throws SelectException 项目不存在异常
     */
    @RequiresLogin()
    @GetMapping("/{projectId}/members")
    public ResponseBean<List<ProjectMemberVo>> getAllProjectMemberByProjectId(@PathVariable("projectId") int projectId) throws SelectException {
        List<ProjectMemberVo> projectMemberVos = projectService.getProjectDetailById(projectId).getMembers();
        return new ResponseBean<>(200, "succ", projectMemberVos);
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
        projectService.saveProjectMemberByProjectId(projectMember, projectId);
        return new ResponseBean<>(200, "succ", null);
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
        projectService.updateProjectMember(projectMember, projectId, memberPhone);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改多个项目成员信息
     *
     * @param projectMembers 修改的多个项目成员
     * @param projectId      项目id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/members/{projectId}")
    public ResponseBean<Object> updateResourceMemberById(@RequestBody List<ProjectMember> projectMembers, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.updateProjectMembers(projectMembers, projectId);
        return new ResponseBean<>(200, "succ", null);
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
        projectService.deleteProjectMember(projectId, memberPhone);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据项目id获取所有项目资源
     *
     * @param projectId 项目id
     * @throws SelectException 项目不存在异常
     */
    @RequiresLogin()
    @GetMapping("/{projectId}/resources")
    public ResponseBean<List<ProjectResourceVo>> getAllProjectResourceByProjectId(@PathVariable("projectId") int projectId) throws SelectException {
        List<ProjectResourceVo> projectResourceVos = projectService.getProjectDetailById(projectId).getResources();
        return new ResponseBean<>(200, "succ", projectResourceVos);
    }

    /**
     * 获取oss私有资源上传签名(用于上传资源文件)
     *
     * @param projectId 项目id
     * @throws PermissionDeniedException 无操作权限异常
     * @throws SelectException           项目不存在异常
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/{projectId}/resources/sign/upload/private")
    public ResponseBean<OssPolicyVo> signUploadPrivateFile(@PathVariable("projectId") int projectId) throws PermissionDeniedException, SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        ProjectDetailVo projectDetailVo = projectService.getProjectDetailById(projectId);
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        OssPolicyVo ossPolicyVo = ossService.signUpload(projectDetailVo.getName() + "/", true);
        return new ResponseBean<>(200, "succ", ossPolicyVo);
    }

    /**
     * 新增项目资源
     *
     * @param projectResourceDto 项目资源
     * @param projectId          项目id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping("/resource/{projectId}")
    public ResponseBean<InsertedIdVo> saveProjectResourceByProjectId(@RequestBody @Validated ProjectResourceDto projectResourceDto, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(phone, projectId)) {
            throw new PermissionDeniedException();
        }
        Integer projectResourceId = projectResourceService.saveProjectResourceByProjectId(projectResourceDto, projectId);
        return new ResponseBean<>(200, "succ", new InsertedIdVo(projectResourceId));
    }

    /**
     * 修改项目资源
     *
     * @param projectResourceDto 修改的项目资源
     * @param projectResourceId  项目资源id
     * @throws PermissionDeniedException 无操作权限异常
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/resource/{projectResourceId}")
    public ResponseBean<Object> updateProjectResourceById(@RequestBody ProjectResourceDto projectResourceDto, @PathVariable("projectResourceId") int projectResourceId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        ProjectResource possibleProjectResource = projectResourceService.getProjectResourceById(projectResourceId);
        if (possibleProjectResource == null || !projectService.hasPermission(phone, possibleProjectResource.getProjectId())) {
            throw new PermissionDeniedException();
        }
        projectResourceService.updateProjectResourceById(projectResourceDto, projectResourceId, possibleProjectResource.getProjectId());
        return new ResponseBean<>(200, "succ", null);
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
        projectResourceService.deleteProjectResourceById(projectResourceId, possibleProjectResource.getProjectId());
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 获取项目资源文件oss下载签名
     *
     * @param projectResourceId 项目资源id
     * @throws SelectException 资源不存在异常
     */
    @RequiresLogin
    @GetMapping("/resource/{projectResourceId}/sign/download")
    public ResponseBean<UrlVo> signDownloadResourceByid(@PathVariable("projectResourceId") int projectResourceId) throws SelectException {
        String url = projectResourceService.signDownloadProjectResourceByid(projectResourceId);
        UrlVo urlVo = new UrlVo();
        urlVo.setUrl(url);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "下载资源", "id=" + projectResourceId);
        return new ResponseBean<>(200, "succ", urlVo);
    }

    /**
     * 获取用户收藏项目列表
     *
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @GetMapping("/collection")
    public ResponseBean<List<UserCollectionVo>> getAllCollection() throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        List<UserCollectionVo> collectionVos = projectService.getAllCollectionByPhone(phone);
        return new ResponseBean<>(200, "succ", collectionVos);
    }

    /**
     * 收藏项目
     *
     * @param projectId 项目id
     * @throws SelectException 用户不存在异常
     * @throws InsertException 插入失败异常
     */
    @RequiresLogin
    @PostMapping("/collection/{projectId}")
    public ResponseBean<Object> collectResource(@PathVariable("projectId") int projectId) throws SelectException, InsertException {
        String phone = ThreadLocalUtil.getCurrentUser();
        projectService.saveCollection(projectId, phone);
        projectService.incrCollectByProjectId(projectId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "收藏项目", "id=" + projectId);
        return new ResponseBean<>(200, "收藏成功！", null);
    }

    /**
     * 取消收藏
     *
     * @param projectId 项目id
     * @throws SelectException 用户不存在异常
     * @throws DeleteException 删除失败异常
     */
    @RequiresLogin
    @DeleteMapping("/collection/{projectId}")
    public ResponseBean<Object> cancelCollection(@PathVariable("projectId") int projectId) throws SelectException, DeleteException {
        String phone = ThreadLocalUtil.getCurrentUser();
        projectService.deleteCollection(projectId, phone);
        projectService.descCollectByProjectId(projectId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "取消收藏资源", "id=" + projectId);
        return new ResponseBean<>(200, "取消收藏成功！", null);
    }

    /**
     * 判断项目是否被收藏
     *
     * @param projectId 项目id
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @GetMapping("/collection/{projectId}")
    public ResponseBean<Boolean> getCollectedByProjectId(@PathVariable("projectId") int projectId) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        boolean result = projectService.hasCollected(projectId, phone);
        return new ResponseBean<>(200, "succ", result);
    }

    /**
     * 增加项目阅读数
     *
     * @param projectId 项目id
     */
    private void incrReadByProjectId(int projectId, String phone) {
        if (redisUtil.get(RedisUtil.USER_PROJECT_READ_PREFIX + phone + ":" + projectId) == null) {
            redisUtil.hincr(RedisUtil.PROJECT_READ_KEY, Integer.toString(projectId), 1);
            redisUtil.set(RedisUtil.USER_PROJECT_READ_PREFIX + phone + ":" + projectId, true, 600);
        }
    }
}