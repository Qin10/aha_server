package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.dao.UserInfoDao;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.DaoException;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * @program: aha_server
 * @description: 用户信息控制类
 * @author: HduStea_YY
 * @create: 2020-10-11 19:35
 **/
@RequestMapping("/userInfo")
@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequiresAuthentication
    @GetMapping("/{phone}")
    public ResponseBean getUserInfoByPhone(@PathVariable("phone") String phone) {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        return new ResponseBean(200, "succ", userInfo, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresAuthentication
    @PostMapping("/avatar")
    public ResponseBean updateUserAvatar(@RequestParam("file") MultipartFile file) throws IOException, DaoException {
        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        String phone = JWTUtil.getAccount(token);
        String filename = userInfoService.updateAvatarFilenameByPhone(file, phone);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("filename", filename);
        return new ResponseBean(200, "头像修改成功！", responseMap, TimeUtil.getFormattedTime(new Date()));
    }
}
