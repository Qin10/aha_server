package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.entity.UserContribPoint;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.ContributionRankService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 贡献点排名相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/rank")
public class ContribtionRankController {
    @Resource
    private ContributionRankService contributionRankService;

    /**
     * 获取贡献点总排行榜
     *
     * @return 排行榜
     */
    @RequiresLogin
    @GetMapping()
    public ResponseBean<List<UserContribPoint>> getRankList() {
        List<UserContribPoint> rankList = contributionRankService.getRankList();
        return new ResponseBean<>(200, "succ", rankList, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 获取用户个人排名
     *
     * @return 用户个人排名和贡献点
     * @throws SelectException 用户不存在异常
     */
    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean<UserContribPoint> getMyRank() throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        UserContribPoint userContribPoint = contributionRankService.getUserContribPointByPhone(phone);
        return new ResponseBean<>(200, "succ", userContribPoint, TimeUtil.getFormattedTime(new Date()));
    }
}
