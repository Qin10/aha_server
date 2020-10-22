package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.CallbackBodyBean;
import cn.hdustea.aha_server.bean.OssPolicyBean;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;
import cn.hdustea.aha_server.exception.apiException.authenticationException.TokenCheckException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.userOperationLog.annotation.LogUserOperation;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.JacksonUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

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
     * @param request HTTP请求
     * @return HTTP响应实体
     */
    @LogUserOperation("获取登录用户信息")
    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean getPersonalUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getAccount(token);
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        return new ResponseBean(200, "succ", userInfo, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 通过手机号查询用户信息的接口
     *
     * @param phone 手机号
     * @return HTTP响应实体
     */
    @LogUserOperation("获取某个用户信息")
    @RequiresLogin
    @GetMapping("/{phone}")
    public ResponseBean getUserInfoByPhone(@PathVariable("phone") String phone) {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        return new ResponseBean(200, "succ", userInfo, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 签名向OSS上传用户头像的请求的接口
     *
     * @param request HTTP请求
     * @return HTTP响应实体
     * @throws Exception 向上抛出异常
     */
    @RequiresLogin
    @GetMapping("/avatar")
    public ResponseBean signUpdateUserAvatar(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getAccount(token);
        OssPolicyBean ossPolicyBean = ossService.signUpload("avatar/" + phone, "userInfo/avatar", phone);
        return new ResponseBean(200, "succ", ossPolicyBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 用户头像上传成功后OSS向服务器的回调接口
     *
     * @param request          HTTP请求
     * @param callbackBodyBean 回调实体的Javabean
     * @return HTTP响应实体
     * @throws Exception 向上抛出异常
     */
    @PostMapping("/avatar")
    public ResponseBean updateUserAvatar(HttpServletRequest request, @RequestBody CallbackBodyBean callbackBodyBean) throws Exception {
        boolean verify = ossService.verifyOSSCallbackRequest(request, JacksonUtil.obj2json(callbackBodyBean));
        if (!verify) {
            throw new TokenCheckException();
        }
        userInfoService.updateAvatarFilenameByPhone(callbackBodyBean.getObject(), callbackBodyBean.getAccount());
        return new ResponseBean(200, "头像修改成功！", null, TimeUtil.getFormattedTime(new Date()));
    }
}
