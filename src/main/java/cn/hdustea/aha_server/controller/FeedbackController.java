package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.FeedbackUserDto;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.FeedbackService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.FeedbackVo;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 反馈类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Resource
    private FeedbackService feedbackService;

    /**
     * 分页获取用户全部反馈
     *
     * @param pageNum      页码
     * @param pageSize     分页大小
     * @param status       处理状态
     * @param type         类别
     * @param lowestLevel  最低级别
     * @param highestLevel 最高级别
     * @param sortBy       排序关键字，取值time、status、type、level、replyTime
     * @param orderBy      排序方式
     */
    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean<PageVo<List<FeedbackVo>>> getAllPersonalFeedback(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "lowestLevel", required = false) Integer lowestLevel, @RequestParam(value = "highestLevel", required = false) Integer highestLevel, @RequestParam(value = "sortBy", required = false, defaultValue = "time") String sortBy, @RequestParam(value = "orderBy", required = false, defaultValue = "desc") String orderBy) throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        PageVo<List<FeedbackVo>> feedbackVos = feedbackService.getAllFeedbackVoPagable(pageNum, pageSize, status, type, userId, lowestLevel, highestLevel, sortBy, orderBy);
        return new ResponseBean<>(200, "succ", feedbackVos);
    }

    /**
     * 用户提交反馈
     *
     * @param feedbackUserDto 反馈
     */
    @RequiresLogin
    @PostMapping()
    public ResponseBean<Object> saveFeedback(@RequestBody @Validated FeedbackUserDto feedbackUserDto) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        feedbackService.saveFeedbackByUserId(feedbackUserDto, userId);
        return new ResponseBean<>(200, "succ", null);
    }
}