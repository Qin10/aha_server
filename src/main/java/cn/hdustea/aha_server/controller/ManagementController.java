package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.ProjectCheckDto;
import cn.hdustea.aha_server.dto.UserDto;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.ProjectService;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.service.UserService;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.vo.UserManagementVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;

    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/project/check/{projectId}")
    public ResponseBean<Object> checkProject(@PathVariable("projectId") int projectId, @RequestBody ProjectCheckDto projectCheckDto) throws UpdateException {
        projectService.checkProjectByProjectId(projectCheckDto, projectId);
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
}
