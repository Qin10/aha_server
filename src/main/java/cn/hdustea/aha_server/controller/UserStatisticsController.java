package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.UserStatistics;
import cn.hdustea.aha_server.service.UserStatisticsService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户统计信息类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/statistics")
public class UserStatisticsController {
    @Resource
    private UserStatisticsService userStatisticsService;

    /**
     * 获取指定用户统计信息
     *
     * @param userId 用户id
     */
    @GetMapping("/{userId}")
    @RequiresLogin
    public ResponseBean<UserStatistics> getUserStatisticsByUserId(@PathVariable("userId") int userId) {
        UserStatistics userStatistics = userStatisticsService.getUserStatisticsByUserId(userId);
        return new ResponseBean<>(200, "succ", userStatistics);
    }

    /**
     * 获取用户个人统计信息
     */
    @GetMapping("/me")
    @RequiresLogin
    public ResponseBean<UserStatistics> getPersonalUserStatisticsByUserId() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        UserStatistics userStatistics = userStatisticsService.getUserStatisticsByUserId(userId);
        return new ResponseBean<>(200, "succ", userStatistics);
    }
}
