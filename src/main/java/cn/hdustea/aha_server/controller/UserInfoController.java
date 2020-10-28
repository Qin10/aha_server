package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.OssPolicyBean;
import cn.hdustea.aha_server.bean.PersonalUserInfoBean;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.userOperationLog.annotation.LogUserOperation;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 用户信息控制类
 *
 * @author STEA_YY
 **/
@RequestMapping("/userInfo")
@RestController
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private OssService ossService;

    /**
     * 获取已登录用户信息（包括全部公有信息和部分私有信息）的接口
     */
    @LogUserOperation("获取登录用户信息")
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/me")
    public ResponseBean getPersonalUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        PersonalUserInfoBean personalUserInfo = userInfoService.getPersonalUserInfo(phone);
        return new ResponseBean(200, "succ", personalUserInfo, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 修改已登录用户公共信息的接口
     *
     * @param userInfo 用户公共信息的实体类
     * @throws UpdateException 修改失败异常
     */
    @RequiresLogin(requireSignNotice = false)
    @PutMapping("/me")
    public ResponseBean updatePersonalUserInfo(HttpServletRequest request, @RequestBody UserInfo userInfo) throws UpdateException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        userInfoService.updateUserInfoByPhone(userInfo, phone);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 通过手机号查询用户信息的接口
     *
     * @param phone 手机号
     */
    @LogUserOperation("获取某个用户信息")
    @RequiresLogin
    @GetMapping("/{phone}")
    public ResponseBean getUserInfoByPhone(@PathVariable("phone") String phone) {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        return new ResponseBean(200, "succ", userInfo, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 签名向OSS上传用户头像的请求的接口，上传后的文件为公共读私有写
     */
    @RequiresLogin(requireSignNotice = false)
    @GetMapping("/avatar/sign/upload")
    public ResponseBean signUpdateUserAvatar(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        OssPolicyBean ossPolicyBean = ossService.signUpload("avatar/" + phone, false);
        return new ResponseBean(200, "succ", ossPolicyBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 修改用户头像文件名的接口
     *
     * @param requestMap 包含了修改后头像文件名的Map对象
     * @throws UpdateException 修改失败异常
     */
    @RequiresLogin(requireSignNotice = false)
    @PostMapping("/avatar")
    public ResponseBean updateUserAvatar(HttpServletRequest request, @RequestBody Map<String, String> requestMap) throws UpdateException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        String filename = requestMap.get("filename");
        userInfoService.updateAvatarFilenameByPhone(filename, phone);
        return new ResponseBean(200, "succ", null, "修改成功！");
    }
}
