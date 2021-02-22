package cn.hdustea.aha_server.controller.management;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.UserDto;
import cn.hdustea.aha_server.entity.Contract;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.ContractService;
import cn.hdustea.aha_server.service.RealNameAuthenticationService;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.service.UserService;
import cn.hdustea.aha_server.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户后台管理类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin/user")
public class UserManagementController {
    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ContractService contractService;
    @Resource
    private RealNameAuthenticationService realNameAuthenticationService;
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
    @GetMapping()
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
    @GetMapping("/{userId}")
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
    @PutMapping("/info/{userId}")
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
    @DeleteMapping("/{userId}")
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
    @GetMapping("/contract/{userId}")
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
    @GetMapping("/contract/signature/{userId}")
    public ResponseBean<Object> getContractSignatureFile(@PathVariable("userId") Integer userId, HttpServletResponse response) throws IOException, SelectException {
        contractService.getContractSignatureFile(userId, response);
        return new ResponseBean<>(200, "succ", null);
    }
    /**
     * 分页获取身份认证信息
     *
     * @param pageNum  页面
     * @param pageSize 分页大小
     * @param status   审核状态
     * @param type     身份认证类型
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/authentication")
    public ResponseBean<PageVo<List<RealNameAuthenticationVo>>> getRealNameAuthenticationVosPagable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "type", required = false) Integer type) {
        PageVo<List<RealNameAuthenticationVo>> realNameAuthenticationVos = realNameAuthenticationService.getRealNameAuthenticationVosPagable(pageNum, pageSize, status, type);
        return new ResponseBean<>(200, "succ", realNameAuthenticationVos);
    }

    /**
     * 根据用户id获取实名认证信息
     *
     * @param userId 用户id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/authentication/{userId}")
    public ResponseBean<RealNameAuthenticationVo> getRealNameAuthenticationVoByUserId(@PathVariable("userId") int userId) throws SelectException {
        RealNameAuthenticationVo realNameAuthenticationVo = realNameAuthenticationService.getRealNameAuthenticationVoByUserId(userId);
        return new ResponseBean<>(200, "succ", realNameAuthenticationVo);
    }

    /**
     * 获取身份认证照片下载签名
     *
     * @param userId   用户id
     * @param fileType 文件类型(取值:studentCard-学生证,idCardFront-身份证正面,idCardBack-身份证反面)
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/authentication/file/{userId}")
    public ResponseBean<CosPolicyVo> getAuthenticationFile(@PathVariable("userId") int userId, @RequestParam(name = "fileType") String fileType) throws SelectException {
        CosPolicyVo cosPolicyVo = realNameAuthenticationService.signDownloadAuthenticationFileByUserIdAndFileType(userId, fileType);
        return new ResponseBean<>(200, "succ", cosPolicyVo);
    }

    /**
     * 审核身份认证信息
     *
     * @param userId 用户id
     * @param status 审核结果(取值:1-通过,2-不通过)
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/authentication/check/{userId}")
    public ResponseBean<Object> checkAuthentication(@PathVariable("userId") int userId, @RequestParam("status") int status) throws SelectException, UpdateException {
        realNameAuthenticationService.checkAuthenticationByUserId(userId, status);
        return new ResponseBean<>(200, "succ", null);
    }
}
