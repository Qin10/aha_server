package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.entity.Resume;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.ResumeService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    /**
     * 根据手机号查看用户简历
     *
     * @param phone 手机号
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @GetMapping("/{phone}")
    public ResponseBean<Resume> getResumeByPhone(@PathVariable("phone") String phone) throws SelectException {
        Resume resume = resumeService.getResumeByPhone(phone);
        return new ResponseBean<>(200, "succ", resume, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 查看当前用户简历
     *
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean<Resume> getPersonalResume() throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        Resume resume = resumeService.getResumeByPhone(phone);
        return new ResponseBean<>(200, "succ", resume, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 修改当前用户简历
     *
     * @param resume 用户简历
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @PutMapping()
    public ResponseBean<Object> updateResume(@RequestBody Resume resume) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        resumeService.updateResumeByPhone(resume, phone);
        return new ResponseBean<>(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }
}