package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.service.CosService;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.CosPolicyVo;
import cn.hdustea.aha_server.vo.OssPolicyVo;
import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户信息相关请求
 *
 * @author STEA_YY
 **/
@Slf4j(topic = "userOperationLog")
@RequestMapping("/userInfo")
@RestController
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private OssService ossService;
    @Resource
    private CosService cosService;

    /**
     * 获取当前用户信息（包括全部详细信息和部分私有信息）
     */
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/me")
    public ResponseBean<PersonalUserInfoVo> getPersonalUserInfo() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        PersonalUserInfoVo personalUserInfo = userInfoService.getPersonalUserInfo(userId);
        return new ResponseBean<>(200, "succ", personalUserInfo);
    }

    /**
     * 修改当前用户详细信息
     *
     * @param userInfo 用户公共信息的实体类
     */
    @RequestLimit(amount = 5, time = 120)
    @RequiresLogin(requireSignNotice = false)
    @PutMapping("/me")
    public ResponseBean<Object> updatePersonalUserInfo(@RequestBody UserInfo userInfo) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        userInfoService.updateUserInfoByUserId(userInfo, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 通过用户id查询用户详细信息
     *
     * @param userId 用户id
     */
    @RequiresLogin
    @GetMapping("/{userId}")
    public ResponseBean<UserInfo> getUserInfoByPhone(@PathVariable("userId") Integer userId) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        return new ResponseBean<>(200, "succ", userInfo);
    }

    /**
     * 获取向OSS上传公共文件签名，用于上传用户头像
     */
    @RequestLimit(amount = 5, time = 120)
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/avatar/sign/upload")
    public ResponseBean<OssPolicyVo> signUpdateUserAvatar() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        OssPolicyVo ossPolicyVo = ossService.signUpload("avatar/" + userId, false);
        return new ResponseBean<>(200, "succ", ossPolicyVo);
    }

    /**
     * 获取向COS上传公共文件签名，用于上传用户头像
     *
     * @param filename 文件名(要上传的文件的全名)
     */
    @RequestLimit(amount = 5, time = 120)
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/avatar/sign/upload/v2")
    public ResponseBean<CosPolicyVo> signUpdateUserAvatarToCos(@RequestParam("filename") String filename) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        CosPolicyVo cosPolicyVo = cosService.signUploadAuthorization("/avatar/" + userId + "/" + filename, false);
        return new ResponseBean<>(200, "succ", cosPolicyVo);
    }
}