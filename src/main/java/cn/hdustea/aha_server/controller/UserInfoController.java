package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.dao.UserInfoDao;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.DaoException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.util.JWTUtil;
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
     * @return
     */
    @RequiresLogin
    @GetMapping("/{phone}")
    public ResponseBean getUserInfoByPhone(@PathVariable("phone") String phone) {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        return new ResponseBean(200, "succ", userInfo, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 修改当前登录用户头像的接口
     *
     * @param file 图片文件
     * @return
     * @throws UpdateException
     */
    @RequiresLogin
    @PostMapping("/avatar")
    public ResponseBean updateUserAvatar(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws UpdateException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getAccount(token);
        String filename = userInfoService.updateAvatarFilenameByPhone(file, phone);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("filename", filename);
        return new ResponseBean(200, "头像修改成功！", responseMap, TimeUtil.getFormattedTime(new Date()));
    }
}
