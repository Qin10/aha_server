package cn.hdustea.aha_server.controller.management;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.config.TencentCosConfig;
import cn.hdustea.aha_server.dto.*;
import cn.hdustea.aha_server.entity.ProjectMember;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.CosService;
import cn.hdustea.aha_server.service.ProjectResourceService;
import cn.hdustea.aha_server.service.ProjectService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目后台管理类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin/project")
public class ProjectManagementController {
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectResourceService projectResourceService;
    @Resource
    private CosService cosService;
    @Resource
    private TencentCosConfig tencentCosConfig;

    /**
     * 分页获取所有项目粗略信息
     *
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @param userId     用户id
     * @param compId     竞赛id
     * @param awardLevel 获奖级别
     * @param sortBy     排序关键字
     * @param orderBy    排序方式
     * @param passed     是否通过审核
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping()
    public ResponseBean<PageVo<List<ProjectRoughVo>>> getAllProjectPageable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "compId", required = false) Integer compId, @RequestParam(value = "awardLevel", required = false) Integer awardLevel, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "passed", required = false) Boolean passed) throws SelectException {
        PageVo<List<ProjectRoughVo>> projectRoughVos = projectService.getAllProjectRoughInfoPagable(pageNum, pageSize, userId, compId, awardLevel, sortBy, orderBy, passed);
        return new ResponseBean<>(200, "succ", projectRoughVos);
    }

    /**
     * 按条件分页获取项目资源
     *
     * @param pageNum        页码
     * @param pageSize       分页大小
     * @param projectId      项目id
     * @param resourcePassed 资源是否通过审核
     * @param projectPassed  资源所在项目是否通过审核
     */
    @RequiresLogin()
    @GetMapping("/resource")
    public ResponseBean<PageVo<List<ProjectResourceVo>>> getAllProjectResourceByConditions(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "projectId", required = false) Integer projectId, @RequestParam(value = "resourcePassed", required = false) Boolean resourcePassed, @RequestParam(value = "projectPassed", required = false) Boolean projectPassed) {
        PageVo<List<ProjectResourceVo>> projectResourceVos = projectResourceService.getAllProjectResourceVoPagable(pageNum, pageSize, resourcePassed, projectPassed, projectId);
        return new ResponseBean<>(200, "succ", projectResourceVos);
    }

    /**
     * 根据项目id获取所有项目资源
     *
     * @param projectId 项目id
     */
    @RequiresLogin()
    @GetMapping("/{projectId}/resources")
    public ResponseBean<List<ProjectResourceVo>> getAllProjectResourceByProjectId(@PathVariable("projectId") int projectId) throws SelectException, PermissionDeniedException {
        List<ProjectResourceVo> projectResourceVos = projectResourceService.getAllProjectResourceVoByConditions(null, null, projectId);
        return new ResponseBean<>(200, "succ", projectResourceVos);
    }

    /**
     * 获取项目资源文件COS下载签名
     *
     * @param projectResourceId 项目资源id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/resource/{projectResourceId}/sign/download")
    public ResponseBean<CosPolicyVo> signDownloadResourceByIdToCos(@PathVariable("projectResourceId") int projectResourceId) throws SelectException {
        CosPolicyVo cosPolicyVo = projectResourceService.signDownloadProjectResourceByIdToCos(projectResourceId);
        return new ResponseBean<>(200, "succ", cosPolicyVo);
    }

    /**
     * 管理员获取COS私有资源上传签名(用于上传资源文件)
     *
     * @param projectId 项目id
     * @param filename  待上传文件名
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/{projectId}/resources/sign/upload/private")
    public ResponseBean<CosPostPolicyVo> signUploadPrivateFileToCos(@PathVariable("projectId") int projectId, @RequestParam("filename") String filename) throws PermissionDeniedException, SelectException {
        CosPostPolicyVo cosPostPolicyVo = cosService.signPostAuthorization("/" + projectId + "/" + filename, tencentCosConfig.getResourceBucketName());
        return new ResponseBean<>(200, "succ", cosPostPolicyVo);
    }

    /**
     * 管理员新建项目并指定作者用户
     *
     * @param projectDto    项目信息
     * @param creatorUserId 创建者用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping()
    public ResponseBean<Integer> saveProjectAndAuthor(@RequestBody @Validated ProjectDto projectDto, @RequestParam int creatorUserId) {
        Integer projectId = projectService.saveProjectAndAuthor(projectDto, creatorUserId);
        return new ResponseBean<>(200, "succ", projectId);
    }

    /**
     * 管理员新增项目资源
     *
     * @param projectResourceDto 项目资源
     * @param projectId          项目id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/resource/{projectId}")
    public ResponseBean<Integer> saveProjectResourceByProjectId(@RequestBody @Validated ProjectResourceDto projectResourceDto, @PathVariable("projectId") int projectId) throws SelectException, InsertException {
        Integer projectResourceId = projectResourceService.saveProjectResourceByProjectId(projectResourceDto, projectId);
        return new ResponseBean<>(200, "succ", projectResourceId);
    }

    /**
     * 管理员创建项目成员
     *
     * @param projectMember 项目成员
     * @param projectId     项目id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/member/{projectId}")
    public ResponseBean<Object> saveProjectMemberByProjectId(@RequestBody ProjectMember projectMember, @PathVariable("projectId") int projectId) throws SelectException, InsertException {
        projectService.saveProjectMemberByProjectId(projectMember, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 审核项目
     *
     * @param projectId       项目id
     * @param projectCheckDto 项目审核相关信息
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/check/{projectId}")
    public ResponseBean<Object> checkProject(@PathVariable("projectId") int projectId, @RequestBody ProjectCheckDto projectCheckDto) throws UpdateException, SelectException {
        projectService.checkProjectByProjectId(projectCheckDto, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 审核项目资源
     *
     * @param resourceId              项目资源id
     * @param projectResourceCheckDto 项目资源审核相关信息
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/resource/check/{resourceId}")
    public ResponseBean<Object> checkProjectResource(@PathVariable("resourceId") int resourceId, @Validated @RequestBody ProjectResourceCheckDto projectResourceCheckDto) throws UpdateException, SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        projectResourceService.checkResourceByResourceId(userId, projectResourceCheckDto, resourceId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 分页获取项目购买记录
     *
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @param resourceId 项目资源id
     * @param projectId  项目id
     * @param userId     用户id
     * @param orderId    订单id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/resource/purchased")
    public ResponseBean<PageVo<List<PurchasedResourceManagementVo>>> getAllPurchasedResourceByResourceId(@RequestParam(value = "resourceId", required = false) Integer resourceId, @RequestParam(value = "projectId", required = false) Integer projectId, @RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "orderId", required = false) Integer orderId, @RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize) {
        PageVo<List<PurchasedResourceManagementVo>> purchasedResources = projectResourceService.getAllPurchasedResourceVoByResourceId(pageNum, pageSize, resourceId, projectId, userId, orderId);
        return new ResponseBean<>(200, "succ", purchasedResources);
    }

    /**
     * 修改项目详细信息
     *
     * @param projectDto 项目信息
     * @param projectId  项目id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/{projectId}")
    public ResponseBean<Object> updateProjectById(@RequestBody ProjectDto projectDto, @PathVariable("projectId") int projectId) {
        projectService.updateProjectByProjectId(projectDto, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除项目
     *
     * @param projectId 项目id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/{projectId}")
    public ResponseBean<Object> deleteProjectById(@PathVariable("projectId") int projectId) {
        projectService.deleteProjectById(projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改项目成员信息
     *
     * @param projectMember 项目成员信息
     * @param projectId     项目id
     * @param memberUserId  项目成员用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/member/{projectId}/{memberUserId}")
    public ResponseBean<Object> updateResourceMemberById(@RequestBody ProjectMember projectMember, @PathVariable("projectId") int projectId, @PathVariable("memberUserId") Integer memberUserId) {
        projectService.updateProjectMember(projectMember, projectId, memberUserId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改多个项目成员信息
     *
     * @param projectMembers 修改的多个项目成员
     * @param projectId      项目id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/members/{projectId}")
    public ResponseBean<Object> updateResourceMemberById(@RequestBody List<ProjectMember> projectMembers, @PathVariable("projectId") int projectId) {
        projectService.updateProjectMembers(projectMembers, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除项目成员
     *
     * @param projectId    项目id
     * @param memberUserId 成员用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/member/{projectId}/{memberUserId}")
    public ResponseBean<Object> deleteProjectMember(@PathVariable("projectId") int projectId, @PathVariable("memberUserId") Integer memberUserId) {
        projectService.deleteProjectMember(projectId, memberUserId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改项目资源信息
     *
     * @param projectResourceUpdateDto 项目资源信息
     * @param projectResourceId        项目资源id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/resource/{projectResourceId}")
    public ResponseBean<Object> updateProjectResourceById(@RequestBody ProjectResourceUpdateDto projectResourceUpdateDto, @PathVariable("projectResourceId") int projectResourceId) {
        projectResourceService.updateProjectResourceById(projectResourceUpdateDto, projectResourceId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除项目资源
     *
     * @param projectResourceId 项目资源id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/resource/{projectResourceId}")
    public ResponseBean<Object> deleteProjectResourceById(@PathVariable("projectResourceId") int projectResourceId) {
        projectResourceService.deleteProjectResourceById(projectResourceId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除资源评论
     *
     * @param projectResourceId 项目资源id
     * @param userId            用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/resource/score/{projectResourceId}")
    public ResponseBean<Object> deleteResourceScore(@PathVariable("projectResourceId") int projectResourceId, @RequestParam("userId") Integer userId) throws DeleteException {
        projectResourceService.deleteResourceScore(projectResourceId, userId);
        return new ResponseBean<>(200, "succ", null);
    }
}
