package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.*;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.*;
import cn.hdustea.aha_server.vo.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 后台管理相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin")
public class ManagementController {
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectResourceService projectResourceService;
    @Resource
    private CompetitionService competitionService;
    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ContractService contractService;
    @Resource
    private MessageService messageService;
    @Resource
    private NoticeService noticeService;
    @Resource
    private FeedbackService feedbackService;

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
    @RequestLimit(time = 5)
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/project")
    public ResponseBean<PageVo<List<ProjectRoughVo>>> getAllProjectPageable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "compId", required = false) Integer compId, @RequestParam(value = "awardLevel", required = false) Integer awardLevel, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "orderBy", required = false) String orderBy, @RequestParam(value = "passed", required = false) Boolean passed) throws SelectException {
        PageVo<List<ProjectRoughVo>> projectRoughVos = projectService.getAllProjectRoughInfoPagable(pageNum, pageSize, userId, compId, awardLevel, sortBy, orderBy, passed);
        return new ResponseBean<>(200, "succ", projectRoughVos);
    }

    /**
     * 获取项目资源文件oss下载签名
     *
     * @param projectResourceId 项目资源id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/project/resource/{projectResourceId}/sign/download")
    public ResponseBean<UrlVo> signDownloadResourceByid(@PathVariable("projectResourceId") int projectResourceId) throws SelectException {
        String url = projectResourceService.signDownloadProjectResourceByid(projectResourceId);
        UrlVo urlVo = new UrlVo();
        urlVo.setUrl(url);
        return new ResponseBean<>(200, "succ", urlVo);
    }

    /**
     * 审核项目
     *
     * @param projectId       项目id
     * @param projectCheckDto 项目审核相关信息
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/project/check/{projectId}")
    public ResponseBean<Object> checkProject(@PathVariable("projectId") int projectId, @RequestBody ProjectCheckDto projectCheckDto) throws UpdateException {
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
    @PostMapping("/project/resource/check/{resourceId}")
    public ResponseBean<Object> checkProjectResource(@PathVariable("resourceId") int resourceId, @RequestBody ProjectResourceCheckDto projectResourceCheckDto) throws UpdateException {
        projectResourceService.checkResourceByResourceId(projectResourceCheckDto, resourceId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 分页获取项目购买记录
     *
     * @param resourceId 项目资源id
     * @param pageNum    页码
     * @param pageSize   分页大小
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/project/resource/purchased/{resourceId}")
    public ResponseBean<PageVo<List<PurchasedResourceManagementVo>>> getAllPurchasedResourceByResourceId(@PathVariable("resourceId") int resourceId, @RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize) {
        PageVo<List<PurchasedResourceManagementVo>> purchasedResources = projectResourceService.getAllPurchasedResourceVoByResourceId(pageNum, pageSize, resourceId);
        return new ResponseBean<>(200, "succ", purchasedResources);
    }

    /**
     * 修改项目详细信息
     *
     * @param projectDto 项目信息
     * @param projectId  项目id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/project/{projectId}")
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
    @DeleteMapping("/project/{projectId}")
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
    @PutMapping("/project/member/{projectId}/{memberUserId}")
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
    @PutMapping("/project/members/{projectId}")
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
    @DeleteMapping("/project/member/{projectId}/{memberUserId}")
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
    @PutMapping("/project/resource/{projectResourceId}")
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
    @DeleteMapping("/project/resource/{projectResourceId}")
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
    @DeleteMapping("/project/resource/score/{projectResourceId}")
    public ResponseBean<Object> deleteResourceScore(@PathVariable("projectResourceId") int projectResourceId, @RequestParam("userId") Integer userId) throws DeleteException {
        projectResourceService.deleteResourceScore(projectResourceId, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 保存竞赛信息
     *
     * @param competition 竞赛信息
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/competition")
    public ResponseBean<Object> saveCompetition(@RequestBody Competition competition) {
        competitionService.saveCompetition(competition);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据竞赛id更新竞赛信息
     *
     * @param id          竞赛id
     * @param competition 更新的竞赛信息
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/competition/{id}")
    public ResponseBean<Object> updateCompetition(@PathVariable("id") int id, @RequestBody Competition competition) {
        competitionService.updateCompetitionById(competition, id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除竞赛信息
     *
     * @param id 竞赛id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/competition/{id}")
    public ResponseBean<Object> deleteCompetition(@PathVariable("id") int id) {
        competitionService.deleteCompetitionById(id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 保存竞赛标签
     *
     * @param competitionType 竞赛标签
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/competition/tag")
    public ResponseBean<Object> saveCompetitionType(@RequestBody @Validated CompetitionType competitionType) {
        competitionService.saveCompetitionType(competitionType);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据竞赛标签id更新竞赛标签
     *
     * @param id              竞赛标签id
     * @param competitionType 更新的竞赛标签
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/competition/tag/{id}")
    public ResponseBean<Object> updateCompetitionType(@PathVariable("id") int id, @RequestBody CompetitionType competitionType) {
        competitionService.updateCompetitionTypeById(competitionType, id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除竞赛标签
     *
     * @param id 竞赛标签id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/competition/tag/{id}")
    public ResponseBean<Object> deleteCompetitionType(@PathVariable("id") int id) {
        competitionService.deleteCompetitionTypeById(id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 分页获取所有用户信息
     *
     * @param pageNum        页码
     * @param pageSize       分页大小
     * @param roleId         角色id
     * @param signedNotice   是否签署服务协议
     * @param signedContract 是否签署合同
     * @param typeId         用户类型
     * @param nicknameLike   模糊昵称
     * @param trueNameLike   模糊真实姓名
     * @param sortBy         排序关键字
     * @param orderBy        排序方式
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/user")
    public ResponseBean<PageVo<List<UserManagementVo>>> getAllUserManagementVoPagable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "roleId", required = false) Integer roleId, @RequestParam(value = "signedNotice", required = false) Boolean signedNotice, @RequestParam(value = "signedContract", required = false) Boolean signedContract, @RequestParam(value = "typeId", required = false) Integer typeId, @RequestParam(value = "nicknameLike", required = false) String nicknameLike, @RequestParam(value = "trueNameLike", required = false) String trueNameLike, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "orderBy", required = false) String orderBy) throws SelectException {
        PageVo<List<UserManagementVo>> userVos = userService.getAllUserManagementVoPagable(pageNum, pageSize, roleId, signedNotice, signedContract, typeId, nicknameLike, trueNameLike, sortBy, orderBy);
        return new ResponseBean<>(200, "succ", userVos);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/user/{userId}")
    public ResponseBean<UserManagementVo> getUserManagementVo(@PathVariable("userId") int userId) {
        UserManagementVo userVo = userService.getUserManagementVoById(userId);
        return new ResponseBean<>(200, "succ", userVo);
    }

    /**
     * 修改用户私有信息
     *
     * @param userDto 用户私有信息
     * @param userId  用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/user/{userId}")
    public ResponseBean<Object> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") int userId) {
        userService.updateUserById(userDto, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改用户公开信息
     *
     * @param userInfo 用户公开信息
     * @param userId   用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/user/info/{userId}")
    public ResponseBean<Object> updateUserInfo(@RequestBody UserInfo userInfo, @PathVariable("userId") int userId) throws SelectException {
        userInfoService.updateUserInfoByUserId(userInfo, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/user/{userId}")
    public ResponseBean<Object> deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUserById(userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 获取用户合同信息
     *
     * @param userId 用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/user/contract/{userId}")
    public ResponseBean<Contract> getContract(@PathVariable("userId") Integer userId) {
        Contract contract = contractService.getContractByUserId(userId);
        return new ResponseBean<>(200, "succ", contract);
    }

    /**
     * 下载用户合同签名
     *
     * @param userId 用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/user/contract/signature/{userId}")
    public ResponseBean<Object> getContractSignatureFile(@PathVariable("userId") Integer userId, HttpServletResponse response) throws IOException, SelectException {
        contractService.getContractSignatureFile(userId, response);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 发送群发系统消息(广播)
     *
     * @param messageDto 站内信
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/message/systemNotice")
    public ResponseBean<Object> sendSystemNotice(@RequestBody MessageDto messageDto) {
        messageService.sendSystemNoticeMessage(messageDto);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 向指定用户发送系统消息
     *
     * @param messageDto 站内信
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/message/systemPrivate")
    public ResponseBean<Object> sendSystemMessage(@RequestBody MessageDto messageDto) {
        messageService.sendSystemMessage(messageDto);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 获取全部公告
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/notice")
    public ResponseBean<List<Notice>> getAllNotice() {
        List<Notice> notices = noticeService.getAllNotice(null, null);
        return new ResponseBean<>(200, "succ", notices);
    }

    /**
     * 发布公告
     *
     * @param noticeDto 公告
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/notice")
    public ResponseBean<Object> sendNotice(@RequestBody @Validated NoticeDto noticeDto) {
        noticeService.saveNotice(noticeDto);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改公告
     *
     * @param noticeDto 公告
     * @param noticeId  公告id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/notice/{noticeId}")
    public ResponseBean<Object> sendNotice(@RequestBody @Validated NoticeDto noticeDto, @PathVariable("noticeId") Integer noticeId) {
        noticeService.updateNoticeById(noticeDto, noticeId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 分页查看用户反馈
     *
     * @param pageNum      页码
     * @param pageSize     分页大小
     * @param userId       用户id
     * @param status       处理状态
     * @param type         反馈类型
     * @param lowestLevel  最低级别
     * @param highestLevel 最高级别
     * @param sortBy       排序关键字，取值time、status、type、level、replyTime
     * @param orderBy      排序方式，取值asc、desc
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/feedback")
    public ResponseBean<PageVo<List<FeedbackVo>>> getAllPersonalFeedback(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "lowestLevel", required = false, defaultValue = "0") Integer lowestLevel, @RequestParam(value = "highestLevel", required = false, defaultValue = "5") Integer highestLevel, @RequestParam(value = "sortBy", required = false, defaultValue = "time") String sortBy, @RequestParam(value = "orderBy", required = false, defaultValue = "desc") String orderBy) throws SelectException {
        PageVo<List<FeedbackVo>> feedbackVos = feedbackService.getAllFeedbackVoPagable(pageNum, pageSize, status, type, userId, lowestLevel, highestLevel, sortBy, orderBy);
        return new ResponseBean<>(200, "succ", feedbackVos);
    }

    /**
     * 管理员处理用户反馈
     *
     * @param feedbackId       反馈id
     * @param feedbackAdminDto 反馈管理员回执
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/feedback/{feedbackId}")
    public ResponseBean<Object> updateFeedbackById(@PathVariable("feedbackId") int feedbackId, @RequestBody FeedbackAdminDto feedbackAdminDto) {
        feedbackService.replyFeedbackById(feedbackAdminDto, feedbackId);
        return new ResponseBean<>(200, "succ", null);
    }
}
