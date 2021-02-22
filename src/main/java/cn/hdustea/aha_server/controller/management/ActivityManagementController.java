package cn.hdustea.aha_server.controller.management;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.ActivityDto;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.ActivityService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动后台管理类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin/activity")
public class ActivityManagementController {
    @Resource
    private ActivityService activityService;

    /**
     * 新建活动
     *
     * @param activityDto 活动信息
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping()
    public ResponseBean<Object> saveActivity(@Validated @RequestBody ActivityDto activityDto) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        activityService.saveActivityAndCreator(activityDto, userId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除活动
     *
     * @param activityId 活动id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/{activityId}")
    public ResponseBean<Object> deleteActivity(@PathVariable("activityId") int activityId) {
        activityService.deleteActivityById(activityId);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 生成指定数量兑换码
     *
     * @param activityId 活动id
     * @param count      生成数量
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/code")
    public ResponseBean<List<String>> generateActivityCode(@RequestParam int activityId, @RequestParam int count) throws SelectException, PermissionDeniedException {
        List<String> codes = activityService.generateCodeById(activityId, count);
        return new ResponseBean<>(200, "succ", codes);
    }

    /**
     * 获取当前已发放兑换码数量
     *
     * @param activityId 活动id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/code/count")
    public ResponseBean<Integer> getCurrentActivityCodeCount(@RequestParam int activityId) {
        Integer currentCount = activityService.getCurrentCountById(activityId);
        return new ResponseBean<>(200, "succ", currentCount);
    }
}
