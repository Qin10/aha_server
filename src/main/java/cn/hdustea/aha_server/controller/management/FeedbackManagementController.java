package cn.hdustea.aha_server.controller.management;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.FeedbackAdminDto;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.FeedbackService;
import cn.hdustea.aha_server.vo.FeedbackVo;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户反馈后台管理相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin/feedback")
public class FeedbackManagementController {

    @Resource
    private FeedbackService feedbackService;

    /**
     * 分页查看用户反馈
     *
     * @param pageNum      页码
     * @param pageSize     分页大小
     * @param userId       用户id
     * @param status       处理状态
     * @param type         反馈类型
     * @param lowestLevel  最低级别
     * @param highestLevel 最高级别
     * @param sortBy       排序关键字，取值time、status、type、level、replyTime
     * @param orderBy      排序方式，取值asc、desc
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping()
    public ResponseBean<PageVo<List<FeedbackVo>>> getAllPersonalFeedback(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "lowestLevel", required = false) Integer lowestLevel, @RequestParam(value = "highestLevel", required = false) Integer highestLevel, @RequestParam(value = "sortBy", required = false, defaultValue = "time") String sortBy, @RequestParam(value = "orderBy", required = false, defaultValue = "desc") String orderBy) throws SelectException {
        PageVo<List<FeedbackVo>> feedbackVos = feedbackService.getAllFeedbackVoPagable(pageNum, pageSize, status, type, userId, lowestLevel, highestLevel, sortBy, orderBy);
        return new ResponseBean<>(200, "succ", feedbackVos);
    }

    /**
     * 管理员处理用户反馈
     *
     * @param feedbackId       反馈id
     * @param feedbackAdminDto 反馈管理员回执
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/{feedbackId}")
    public ResponseBean<Object> updateFeedbackById(@PathVariable("feedbackId") int feedbackId, @RequestBody FeedbackAdminDto feedbackAdminDto) {
        feedbackService.replyFeedbackById(feedbackAdminDto, feedbackId);
        return new ResponseBean<>(200, "succ", null);
    }
}
