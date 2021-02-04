package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.service.ActivityService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @PostMapping("/code")
    @RequiresLogin
    public ResponseBean<Object> exchangeCode(@RequestParam String code) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        activityService.exchangeCode(code, userId);
        return new ResponseBean<>(200, "succ", null);
    }
}
