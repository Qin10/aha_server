package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.Resume;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.ResumeService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户简历相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Resource
    private ResumeService resumeService;

    /**
     * 根据用户id查看用户简历
     *
     * @param userId 用户id
     */
    @RequiresLogin
    @GetMapping("/{userId}")
    public ResponseBean<Resume> getResumeByPhone(@PathVariable("userId") Integer userId) {
        Resume resume = resumeService.getResumeByUserId(userId);
        return new ResponseBean<>(200, "succ", resume);
    }

    /**
     * 查看当前用户简历
     */
    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean<Resume> getPersonalResume() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Resume resume = resumeService.getResumeByUserId(userId);
        return new ResponseBean<>(200, "succ", resume);
    }

    /**
     * 修改当前用户简历
     *
     * @param resume 用户简历
     */
    @RequestLimit(amount = 4)
    @RequiresLogin
    @PutMapping()
    public ResponseBean<Object> updateResume(@RequestBody Resume resume) throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        resumeService.updateResumeByUserId(resume, userId);
        return new ResponseBean<>(200, "succ", null);
    }
}