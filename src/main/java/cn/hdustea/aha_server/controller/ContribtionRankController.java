package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.ContributionRankService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    public ResponseBean<List<UserContribPointVo>> getRankList() {
        List<UserContribPointVo> rankList = contributionRankService.getRankList();
        return new ResponseBean<>(200, "succ", rankList);
    }

    /**
     * 获取用户个人排名
     *
     * @return 用户个人排名和贡献点
     */
    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean<UserContribPointVo> getMyRank() throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        UserContribPointVo userContribPointVo = contributionRankService.getUserContribPointByUserId(userId);
        return new ResponseBean<>(200, "succ", userContribPointVo);
    }
}
