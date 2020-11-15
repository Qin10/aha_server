package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.vo.OssPolicyVo;
import cn.hdustea.aha_server.vo.PersonalUserInfoVo;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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

    /**
     * 获取当前用户信息（包括全部详细信息和部分私有信息）
     */
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/me")
    public ResponseBean<PersonalUserInfoVo> getPersonalUserInfo() throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        PersonalUserInfoVo personalUserInfo = userInfoService.getPersonalUserInfo(phone);
        return new ResponseBean<>(200, "succ", personalUserInfo);
    }

    /**
     * 修改当前用户详细信息
     *
     * @param userInfo 用户公共信息的实体类
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin(requireSignNotice = false)
    @PutMapping("/me")
    public ResponseBean<Object> updatePersonalUserInfo(@RequestBody UserInfo userInfo) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        userInfoService.updateUserInfoByPhone(userInfo, phone);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 通过手机号查询用户详细信息
     *
     * @param phone 手机号
     */
    @RequiresLogin
    @GetMapping("/{phone}")
    public ResponseBean<UserInfo> getUserInfoByPhone(@PathVariable("phone") String phone) throws SelectException {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        return new ResponseBean<>(200, "succ", userInfo);
    }

    /**
     * 获取向OSS上传公共文件签名，用于上传用户头像
     */
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/avatar/sign/upload")
    public ResponseBean<OssPolicyVo> signUpdateUserAvatar() {
        String phone = ThreadLocalUtil.getCurrentUser();
        OssPolicyVo ossPolicyVo = ossService.signUpload("avatar/" + phone, false);
        return new ResponseBean<>(200, "succ", ossPolicyVo);
    }

    /**
     * 修改用户头像文件名
     *
     * @param requestMap 包含了修改后头像文件名的Map对象
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin(requireSignNotice = false)
    @PostMapping("/avatar")
    public ResponseBean<Object> updateUserAvatar(@RequestBody Map<String, String> requestMap) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        String fileUrl = requestMap.get("fileUrl");
        userInfoService.updateAvatarUrlByPhone(fileUrl, phone);
        return new ResponseBean<>(200, "修改成功！", null);
    }
}