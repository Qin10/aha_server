package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.dto.ProjectDto;
import cn.hdustea.aha_server.dto.ProjectResourceDto;
import cn.hdustea.aha_server.dto.ProjectResourceScoreDto;
import cn.hdustea.aha_server.dto.ProjectResourceUpdateDto;
import cn.hdustea.aha_server.entity.ProjectMember;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.*;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private CosService cosService;
    @Resource
    private RedisService redisService;
    @Resource
    private UserOperationLogConfig userOperationLogConfig;

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
     */
    @RequestLimit(time = 5)
    @RequiresLogin
    @GetMapping()
    public ResponseBean<PageVo<List<ProjectRoughVo>>> getAllProjectPageable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "compId", required = false) Integer compId, @RequestParam(value = "awardLevel", required = false) Integer awardLevel, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "orderBy", required = false) String orderBy) throws SelectException {
        PageVo<List<ProjectRoughVo>> projectRoughVos = projectService.getAllProjectRoughInfoPagable(pageNum, pageSize, userId, compId, awardLevel, sortBy, orderBy, true);
        return new ResponseBean<>(200, "succ", projectRoughVos);
    }

    /**
     * 分页获取登录用户所有项目粗略信息
     *
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @param compId     竞赛id
     * @param awardLevel 获奖级别
     * @param sortBy     排序关键字
     * @param orderBy    排序方式
     */
    @RequestLimit(time = 5)
    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean<PageVo<List<ProjectRoughVo>>> getAllPersonalProjectPageable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "compId", required = false) Integer compId, @RequestParam(value = "awardLevel", required = false) Integer awardLevel, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "orderBy", required = false) String orderBy) throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Boolean passed = null;
        PageVo<List<ProjectRoughVo>> projectRoughVos = projectService.getAllProjectRoughInfoPagable(pageNum, pageSize, userId, compId, awardLevel, sortBy, orderBy, passed);
        return new ResponseBean<>(200, "succ", projectRoughVos);
    }

    /**
     * 根据项目id获取项目详细信息
     *
     * @param projectId 项目id
     */
    @RequestLimit(time = 30)
    @RequiresLogin
    @GetMapping("/{projectId}")
    public ResponseBean<ProjectDetailVo> getProjectById(@PathVariable("projectId") int projectId) throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        ProjectDetailVo projectDetailVo = projectService.getProjectDetailById(projectId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "查看项目", "id=" + projectId);
        incrReadByProjectId(projectId, userId);
        return new ResponseBean<>(200, "succ", projectDetailVo);
    }

    /**
     * 获取oss公开资源上传签名(用于上传项目头像和获奖证明材料)
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/sign/upload/public")
    public ResponseBean<OssPolicyVo> signUploadPublicFile() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        OssPolicyVo ossPolicyVo = ossService.signUpload("resource/" + userId, false);
        return new ResponseBean<>(200, "succ", ossPolicyVo);
    }

    /**
     * 获取COS公开资源上传签名(用于上传项目头像和获奖证明材料)
     *
     * @param filename 待上传文件名
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/sign/upload/public/v2")
    public ResponseBean<CosPolicyVo> signUploadPublicFileToCos(@RequestParam("filename") String filename) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        CosPolicyVo cosPolicyVo = cosService.signUploadAuthorization("/resource/" + userId + "/" + filename, false);
        return new ResponseBean<>(200, "succ", cosPolicyVo);
    }

    /**
     * 新增项目
     *
     * @param projectDto 项目信息
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping()
    public ResponseBean<InsertedIdVo> saveProject(@RequestBody @Validated ProjectDto projectDto) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer projectId = projectService.saveProjectAndAuthor(projectDto, userId);
        return new ResponseBean<>(200, "succ", new InsertedIdVo(projectId));
    }

    /**
     * 修改项目信息
     *
     * @param projectDto 修改的项目信息
     * @param projectId  项目id
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/{projectId}")
    public ResponseBean<Object> updateProjectById(@RequestBody ProjectDto projectDto, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(userId, projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.updateProjectByProjectId(projectDto, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除项目
     *
     * @param projectId 项目id
     */
    @RequiresLogin(requireSignContract = true)
    @DeleteMapping("/{projectId}")
    public ResponseBean<Object> deleteProjectById(@PathVariable("projectId") int projectId) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(userId, projectId)) {
            throw new PermissionDeniedException();
        }
        if (projectService.isPassed(projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.deleteProjectById(projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据项目id获取所有项目成员
     *
     * @param projectId 项目id
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
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping("/member/{projectId}")
    public ResponseBean<Object> saveProjectMemberById(@RequestBody @Validated ProjectMember projectMember, @PathVariable("projectId") int projectId) throws PermissionDeniedException, InsertException, SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(userId, projectId)) {
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
     * @param memberUserId  成员用户id
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/member/{projectId}/{memberUserId}")
    public ResponseBean<Object> updateResourceMemberById(@RequestBody ProjectMember projectMember, @PathVariable("projectId") int projectId, @PathVariable("memberUserId") Integer memberUserId) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(userId, projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.updateProjectMember(projectMember, projectId, memberUserId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改多个项目成员信息
     *
     * @param projectMembers 修改的多个项目成员
     * @param projectId      项目id
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/members/{projectId}")
    public ResponseBean<Object> updateResourceMemberById(@RequestBody List<ProjectMember> projectMembers, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(userId, projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.updateProjectMembers(projectMembers, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除项目成员
     *
     * @param projectId    项目id
     * @param memberUserId 成员用户id
     */
    @RequiresLogin
    @DeleteMapping("/member/{projectId}/{memberUserId}")
    public ResponseBean<Object> deleteProjectMember(@PathVariable("projectId") int projectId, @PathVariable("memberUserId") Integer memberUserId) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(userId, projectId)) {
            throw new PermissionDeniedException();
        }
        projectService.deleteProjectMember(projectId, memberUserId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据项目id获取所有项目资源
     *
     * @param projectId 项目id
     */
    @RequiresLogin()
    @GetMapping("/{projectId}/resources")
    public ResponseBean<List<ProjectResourceVo>> getAllProjectResourceByProjectId(@PathVariable("projectId") int projectId, @RequestParam(value = "edit", defaultValue = "false", required = false) boolean edit) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        List<ProjectResourceVo> projectResourceVos;
        if (!edit) {
            projectResourceVos = projectResourceService.getAllProjectResourceVoByConditions(true, projectId);
        } else {
            if (!projectService.hasPermission(userId, projectId)) {
                throw new PermissionDeniedException();
            }
            projectResourceVos = projectResourceService.getAllProjectResourceVoByConditions(null, projectId);
        }
        return new ResponseBean<>(200, "succ", projectResourceVos);
    }

    /**
     * 根据项目资源id获取项目资源
     *
     * @param projectResourceId 项目资源id
     */
    @RequiresLogin()
    @GetMapping("/resource/{projectResourceId}")
    public ResponseBean<ProjectResourceVo> getProjectResourceByResourceId(@PathVariable("projectResourceId") int projectResourceId) {
        ProjectResourceVo projectResourceVo = projectResourceService.getProjectResourceVoById(projectResourceId);
        return new ResponseBean<>(200, "succ", projectResourceVo);
    }

    /**
     * 获取oss私有资源上传签名(用于上传资源文件)
     *
     * @param projectId 项目id
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/{projectId}/resources/sign/upload/private")
    public ResponseBean<OssPolicyVo> signUploadPrivateFile(@PathVariable("projectId") int projectId) throws PermissionDeniedException, SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        ProjectDetailVo projectDetailVo = projectService.getProjectDetailById(projectId);
        if (!projectService.hasPermission(userId, projectId)) {
            throw new PermissionDeniedException();
        }
        OssPolicyVo ossPolicyVo = ossService.signUpload(projectDetailVo.getName() + "/", true);
        return new ResponseBean<>(200, "succ", ossPolicyVo);
    }

    /**
     * 获取COS私有资源上传签名(用于上传资源文件)
     *
     * @param projectId 项目id
     * @param filename  待上传文件名
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/{projectId}/resources/sign/upload/private/v2")
    public ResponseBean<CosPolicyVo> signUploadPrivateFileToCos(@PathVariable("projectId") int projectId, @RequestParam("filename") String filename) throws PermissionDeniedException, SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        projectService.getProjectDetailById(projectId);
        if (!projectService.hasPermission(userId, projectId)) {
            throw new PermissionDeniedException();
        }
        CosPolicyVo cosPolicyVo = cosService.signUploadAuthorization("/" + projectId + "/" + filename, true);
        return new ResponseBean<>(200, "succ", cosPolicyVo);
    }

    /**
     * 新增项目资源
     *
     * @param projectResourceDto 项目资源
     * @param projectId          项目id
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping("/resource/{projectId}")
    public ResponseBean<InsertedIdVo> saveProjectResourceByProjectId(@RequestBody @Validated ProjectResourceDto projectResourceDto, @PathVariable("projectId") int projectId) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectService.hasPermission(userId, projectId)) {
            throw new PermissionDeniedException();
        }
        Integer projectResourceId = projectResourceService.saveProjectResourceByProjectId(projectResourceDto, projectId);
        return new ResponseBean<>(200, "succ", new InsertedIdVo(projectResourceId));
    }

    /**
     * 更新项目资源
     *
     * @param projectResourceId        项目资源id
     * @param projectResourceUpdateDto 项目资源信息
     */
    @RequiresLogin(requireSignContract = true)
    @PutMapping("/resource/{projectResourceId}")
    public ResponseBean<Object> updateProjectResourceByResourceId(@PathVariable("projectResourceId") int projectResourceId, @RequestBody ProjectResourceUpdateDto projectResourceUpdateDto) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectResourceService.hasPermission(userId, projectResourceId)) {
            throw new PermissionDeniedException();
        }
        projectResourceService.updateProjectResourceById(projectResourceUpdateDto, projectResourceId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除项目资源
     *
     * @param projectResourceId 项目资源id
     */
    @RequiresLogin(requireSignContract = true)
    @DeleteMapping("/resource/{projectResourceId}")
    public ResponseBean<Object> deleteProjectResourceById(@PathVariable("projectResourceId") int projectResourceId) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectResourceService.hasPermission(userId, projectResourceId)) {
            throw new PermissionDeniedException();
        }
        if (!projectResourceService.isPassed(projectResourceId)) {
            throw new PermissionDeniedException();
        }
        projectResourceService.deleteProjectResourceById(projectResourceId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 获取项目资源文件oss下载签名
     *
     * @param projectResourceId 项目资源id
     */
    @RequestLimit()
    @RequiresLogin
    @GetMapping("/resource/{projectResourceId}/sign/download")
    public ResponseBean<UrlVo> signDownloadResourceByid(@PathVariable("projectResourceId") int projectResourceId) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectResourceService.purchasedResource(userId, projectResourceId)) {
            if (!projectResourceService.hasPermission(userId, projectResourceId)) {
                throw new PermissionDeniedException("您尚未购买本资源！");
            }
        }
        String url = projectResourceService.signDownloadProjectResourceByid(projectResourceId);
        UrlVo urlVo = new UrlVo();
        urlVo.setUrl(url);
        projectResourceService.incrDownloadById(projectResourceId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "下载资源", "id=" + projectResourceId);
        return new ResponseBean<>(200, "succ", urlVo);
    }

    /**
     * 获取项目资源文件COS下载签名
     *
     * @param projectResourceId 项目资源id
     */
    @RequestLimit()
    @RequiresLogin
    @GetMapping("/resource/{projectResourceId}/sign/download/v2")
    public ResponseBean<CosPolicyVo> signDownloadResourceByIdToCos(@PathVariable("projectResourceId") int projectResourceId) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
//        if (!projectResourceService.allowDownload(userId, projectResourceId)) {
//            throw new PermissionDeniedException("您无权限下载本资源！");
//        }
        CosPolicyVo cosPolicyVo = projectResourceService.signDownloadProjectResourceByIdToCos(projectResourceId);
        projectResourceService.incrDownloadById(projectResourceId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "下载资源", "id=" + projectResourceId);
        return new ResponseBean<>(200, "succ", cosPolicyVo);
    }

    /**
     * 获取项目资源文件COS阅读签名
     *
     * @param projectResourceId 项目资源id
     */
    @RequestLimit()
    @RequiresLogin
    @GetMapping("/resource/{projectResourceId}/sign/read/v2")
    public ResponseBean<CosPolicyVo> signPreviewResourceByIdToCos(@PathVariable("projectResourceId") int projectResourceId) throws SelectException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
//        if (!projectResourceService.purchasedResource(userId, projectResourceId)) {
//            if (!projectResourceService.hasPermission(userId, projectResourceId)) {
//                throw new PermissionDeniedException("您尚未购买本资源！");
//            }
//        }
        CosPolicyVo cosPolicyVo = projectResourceService.signPreviewProjectResourceByIdToCos(projectResourceId);
        projectResourceService.incrDownloadById(projectResourceId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "阅读资源", "id=" + projectResourceId);
        return new ResponseBean<>(200, "succ", cosPolicyVo);
    }

    /**
     * 获取用户收藏项目列表
     */
    @RequiresLogin
    @GetMapping("/collection")
    public ResponseBean<List<UserCollectionVo>> getAllCollection() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        List<UserCollectionVo> collectionVos = projectService.getAllCollectionByUserId(userId);
        return new ResponseBean<>(200, "succ", collectionVos);
    }

    /**
     * 收藏项目
     *
     * @param projectId 项目id
     */
    @RequestLimit(time = 30)
    @RequiresLogin
    @PostMapping("/collection/{projectId}")
    public ResponseBean<Object> collectResource(@PathVariable("projectId") int projectId) throws InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        projectService.saveCollection(projectId, userId);
        projectService.incrCollectByProjectId(projectId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "收藏项目", "id=" + projectId);
        return new ResponseBean<>(200, "收藏成功！", null);
    }

    /**
     * 取消收藏
     *
     * @param projectId 项目id
     */
    @RequestLimit(time = 30)
    @RequiresLogin
    @DeleteMapping("/collection/{projectId}")
    public ResponseBean<Object> cancelCollection(@PathVariable("projectId") int projectId) throws DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        projectService.deleteCollection(projectId, userId);
        projectService.descCollectByProjectId(projectId);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "取消收藏资源", "id=" + projectId);
        return new ResponseBean<>(200, "取消收藏成功！", null);
    }

    /**
     * 判断项目是否被收藏
     *
     * @param projectId 项目id
     */
    @RequiresLogin
    @GetMapping("/collection/check/{projectId}")
    public ResponseBean<Boolean> getCollectedByProjectId(@PathVariable("projectId") int projectId) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean result = projectService.hasCollected(projectId, userId);
        return new ResponseBean<>(200, "succ", result);
    }

    /**
     * 分页查看资源评价信息
     *
     * @param pageNum      页码
     * @param pageSize     分页大小
     * @param projectId    项目id
     * @param resourceId   项目资源id
     * @param lowestScore  最低分
     * @param highestScore 最高分
     * @param sortBy       排序关键字,取值time、score
     * @param orderBy      排序方式
     */
    @RequiresLogin
    @GetMapping("/resource/score")
    public ResponseBean<PageVo<List<ProjectResourceScoreVo>>> getAllResourceScoreById(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "projectId", required = false) Integer projectId, @RequestParam(value = "resourceId", required = false) Integer resourceId, @RequestParam(value = "lowestScore", required = false, defaultValue = "0.0") BigDecimal lowestScore, @RequestParam(value = "highestScore", required = false, defaultValue = "5.0") BigDecimal highestScore, @RequestParam(value = "sortBy", required = false, defaultValue = "time") String sortBy, @RequestParam(value = "orderBy", required = false, defaultValue = "desc") String orderBy) throws SelectException {
        PageVo<List<ProjectResourceScoreVo>> projectResourceScoreVos = projectResourceService.getAllResourceScorePagable(pageNum, pageSize, projectId, resourceId, lowestScore, highestScore, sortBy, orderBy);
        return new ResponseBean<>(200, "succ", projectResourceScoreVos);
    }

    /**
     * 评价项目资源
     *
     * @param projectResourceScoreDto 资源评分信息
     * @param projectResourceId       项目资源id
     */
    @RequiresLogin
    @PostMapping("/resource/score/{projectResourceId}")
    public ResponseBean<Object> saveResourceScore(@RequestBody @Validated ProjectResourceScoreDto projectResourceScoreDto, @PathVariable("projectResourceId") int projectResourceId) throws InsertException, PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (!projectResourceService.purchasedResource(userId, projectResourceId)) {
            throw new PermissionDeniedException("您尚未购买本资源！");
        }
        projectResourceService.saveResourceScore(projectResourceScoreDto, projectResourceId, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除项目资源评价
     *
     * @param projectResourceId 项目资源id
     */
    @RequiresLogin
    @DeleteMapping("/resource/score/{projectResourceId}")
    public ResponseBean<Object> deleteResourceScore(@PathVariable("projectResourceId") Integer projectResourceId) throws DeleteException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        projectResourceService.deleteResourceScore(projectResourceId, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 查看全部已购资源
     */
    @RequiresLogin
    @GetMapping("/resource/purchased")
    public ResponseBean<List<PurchasedResourceVo>> getAllPurchasedResource() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        List<PurchasedResourceVo> purchasedResourceVos = projectResourceService.getAllPurchasedResourceVoByUserId(userId);
        return new ResponseBean<>(200, "succ", purchasedResourceVos);
    }

    /**
     * 判断项目资源是否已经购买
     *
     * @param resourceId 项目资源id
     */
    @RequiresLogin
    @GetMapping("/resource/purchased/check/{resourceId}")
    public ResponseBean<Boolean> checkPurchasedResource(@PathVariable("resourceId") int resourceId) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        boolean purchased = projectResourceService.purchasedResource(userId, resourceId);
        return new ResponseBean<>(200, "succ", purchased);
    }

    /**
     * 根据项目id获取用户已购资源的id数组
     *
     * @param projectId 项目id
     */
    @RequiresLogin
    @GetMapping("/purchased/{projectId}")
    public ResponseBean<Integer[]> getAllPersonalResourcePurchasedVoByProjectId(@PathVariable("projectId") int projectId) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer[] purchasedResourceIds = projectResourceService.getAllPurchasedResourceIdsByProjectIdAndUserId(projectId, userId);
        return new ResponseBean<>(200, "succ", purchasedResourceIds);
    }

    /**
     * 增加项目阅读数
     *
     * @param projectId 项目id
     */
    private void incrReadByProjectId(int projectId, Integer userId) {
        if (redisService.get(RedisConstants.USER_PROJECT_READ_PREFIX + userId + ":" + projectId) == null) {
            redisService.hIncr(RedisConstants.PROJECT_READ_KEY, Integer.toString(projectId), 1);
            redisService.set(RedisConstants.USER_PROJECT_READ_PREFIX + userId + ":" + projectId, true, 600);
        }
    }
}