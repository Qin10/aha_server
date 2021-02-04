package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.Activity;
import cn.hdustea.aha_server.entity.ActivityCodeExchangeLog;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.ActivityService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    /**
     * 分页获取活动信息
     *
     * @param pageNum  页码
     * @param pageSize 分页大小
     */
    @GetMapping()
    public ResponseBean<PageVo<List<Activity>>> getActivitiesPagable(@RequestParam int pageNum, @RequestParam int pageSize) {
        PageVo<List<Activity>> activities = activityService.getActivitiesPagable(pageNum, pageSize);
        return new ResponseBean<>(200, "succ", activities);
    }

    /**
     * 获取用户个人兑换日志
     *
     * @param pageNum  页码
     * @param pageSize 分页大小
     */
    @GetMapping("/log/me")
    public ResponseBean<PageVo<List<ActivityCodeExchangeLog>>> getPersonalExchangeLogsPagable(@RequestParam int pageNum, @RequestParam int pageSize) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        PageVo<List<ActivityCodeExchangeLog>> activityCodeExchangeLogs = activityService.getActivityCodeExchangeLogPagable(pageNum, pageSize, userId, null);
        return new ResponseBean<>(200, "succ", activityCodeExchangeLogs);
    }

    /**
     * 根据活动id获取活动信息
     *
     * @param id 活动id
     */
    @GetMapping("/{id}")
    public ResponseBean<Activity> getActivityById(@PathVariable("id") int id) throws SelectException {
        Activity activity = activityService.getExistedActivityById(id);
        return new ResponseBean<>(200, "succ", activity);
    }

    /**
     * 使用兑换码
     *
     * @param code 兑换码
     */
    @PostMapping("/code")
    @RequiresLogin
    public ResponseBean<Object> exchangeCode(@RequestParam String code) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        activityService.exchangeCode(code, userId);
        return new ResponseBean<>(200, "succ", null);
    }
}
