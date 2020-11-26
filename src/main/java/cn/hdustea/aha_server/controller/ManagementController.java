package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.ProjectCheckDto;
import cn.hdustea.aha_server.dto.ProjectDto;
import cn.hdustea.aha_server.dto.UserDto;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.*;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.vo.UrlVo;
import cn.hdustea.aha_server.vo.UserManagementVo;
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

    /**
     * 获取项目资源文件oss下载签名
     *
     * @param projectResourceId 项目资源id
     * @throws SelectException 资源不存在异常
     */
    @RequiresLogin
    @GetMapping("/project/resource/{projectResourceId}/sign/download")
    public ResponseBean<UrlVo> signDownloadResourceByid(@PathVariable("projectResourceId") int projectResourceId) throws SelectException {
        String url = projectResourceService.signDownloadProjectResourceByid(projectResourceId);
        UrlVo urlVo = new UrlVo();
        urlVo.setUrl(url);
        return new ResponseBean<>(200, "succ", urlVo);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/project/check/{projectId}")
    public ResponseBean<Object> checkProject(@PathVariable("projectId") int projectId, @RequestBody ProjectCheckDto projectCheckDto) throws UpdateException {
        projectService.checkProjectByProjectId(projectCheckDto, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/project/{projectId}")
    public ResponseBean<Object> updateProjectById(@RequestBody ProjectDto projectDto, @PathVariable("projectId") int projectId) {
        projectService.updateProjectByProjectId(projectDto, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/project/{projectId}")
    public ResponseBean<Object> deleteProjectById(@PathVariable("projectId") int projectId) {
        projectService.deleteProjectById(projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/project/member/{projectId}/{memberPhone}")
    public ResponseBean<Object> updateResourceMemberById(@RequestBody ProjectMember projectMember, @PathVariable("projectId") int projectId, @PathVariable("memberPhone") String memberPhone) {
        projectService.updateProjectMember(projectMember, projectId, memberPhone);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/project/members/{projectId}")
    public ResponseBean<Object> updateResourceMemberById(@RequestBody List<ProjectMember> projectMembers, @PathVariable("projectId") int projectId) {
        projectService.updateProjectMembers(projectMembers, projectId);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/project/member/{projectId}/{memberPhone}")
    public ResponseBean<Object> deleteProjectMember(@PathVariable("projectId") int projectId, @PathVariable("memberPhone") String memberPhone) {
        projectService.deleteProjectMember(projectId, memberPhone);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/project/resource/{projectResourceId}")
    public ResponseBean<Object> updateProjectResourceById(@RequestBody ProjectResource projectResource, @PathVariable("projectResourceId") int projectResourceId) {
        projectResourceService.updateProjectResourceById(projectResource, projectResourceId);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/project/resource/{projectResourceId}")
    public ResponseBean<Object> deleteProjectResourceById(@PathVariable("projectResourceId") int projectResourceId) {
        projectResourceService.deleteProjectResourceById(projectResourceId);
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

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/competition/{id}")
    public ResponseBean<Object> deleteCompetition(@PathVariable("id") int id) {
        competitionService.deleteCompetitionById(id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 保存竞赛标签
     *
     * @param competitionTag 竞赛标签
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/competition/tag")
    public ResponseBean<Object> saveCompetitionTag(@RequestBody @Validated CompetitionTag competitionTag) {
        competitionService.saveCompetitionTag(competitionTag);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据竞赛标签id更新竞赛标签
     *
     * @param id             竞赛标签id
     * @param competitionTag 更新的竞赛标签
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/competition/tag/{id}")
    public ResponseBean<Object> updateCompetitionTag(@PathVariable("id") int id, @RequestBody CompetitionTag competitionTag) {
        competitionService.updateCompetitionTagById(competitionTag, id);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/competition/tag/{id}")
    public ResponseBean<Object> deleteCompetitionTag(@PathVariable("id") int id) {
        competitionService.deleteCompetitionTagById(id);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/user")
    public ResponseBean<PageVo<List<UserManagementVo>>> getAllUserManagementVoPagable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "roleId", required = false) Integer roleId, @RequestParam(value = "signedNotice", required = false) Boolean signedNotice, @RequestParam(value = "signedContract", required = false) Boolean signedContract, @RequestParam(value = "typeId", required = false) Integer typeId, @RequestParam(value = "phoneLike", required = false) String phoneLike, @RequestParam(value = "nicknameLike", required = false) String nicknameLike, @RequestParam(value = "trueNameLike", required = false) String trueNameLike, @RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "orderBy", required = false) String orderBy) {
        PageVo<List<UserManagementVo>> userVos = userService.getAllUserManagementVoPagable(pageNum, pageSize, roleId, signedNotice, signedContract, typeId, phoneLike, nicknameLike, trueNameLike, sortBy, orderBy);
        return new ResponseBean<>(200, "succ", userVos);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/user/{userId}")
    public ResponseBean<UserManagementVo> getAllUserManagementVoPagable(@PathVariable("userId") int userId) {
        UserManagementVo userVo = userService.getUserManagementVoById(userId);
        return new ResponseBean<>(200, "succ", userVo);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/user/{userId}")
    public ResponseBean<Object> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") int userId) {
        userService.updateUserById(userDto, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/user/info/{userId}")
    public ResponseBean<Object> updateUserInfo(@RequestBody UserInfo userInfo, @PathVariable("userId") int userId) {
        userInfoService.updateUserInfoByUserId(userInfo, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/user/{userId}")
    public ResponseBean<Object> deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUserById(userId);
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/user/contract/{userId}")
    public ResponseBean<Contract> getContract(@PathVariable("userId") int userId) {
        Contract contract = contractService.getContractByUserId(userId);
        return new ResponseBean<>(200, "succ", contract);
    }

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/user/contract/signature/{userId}")
    public ResponseBean<Contract> getContractSignatureFile(@PathVariable("userId") int userId, HttpServletResponse response) throws IOException, SelectException {
        contractService.getContractSignatureFile(userId, response);
        return new ResponseBean<>(200, "succ", null);
    }
}
