package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.Resume;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.ResumeService;
import cn.hdustea.aha_server.service.UserInfoService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户简历的控制器
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Resource
    private ResumeService resumeService;
    @Resource
    private UserInfoService userInfoService;

    @GetMapping("/{phone}")
    public ResponseBean getResumeByPhone(@PathVariable("phone") String phone) throws SelectException {
        Resume resume = resumeService.getResumeByPhone(phone);
        return new ResponseBean(200, "succ", resume, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PostMapping()
    public ResponseBean saveResume(HttpServletRequest request, @RequestBody Resume resume) throws UpdateException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        Resume savedResume = resumeService.saveResume(resume);
        userInfoService.updateResumeIdByPhone(savedResume.getId().toString(), phone);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PutMapping()
    public ResponseBean updateResume(HttpServletRequest request, @RequestBody Resume resume) throws UpdateException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        resumeService.updateResumeByPhone(resume, phone);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }
}