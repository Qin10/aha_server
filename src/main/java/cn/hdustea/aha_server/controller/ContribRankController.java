package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.UserContribPoint;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.ContribRankService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 贡献点排名控制类
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/rank")
public class ContribRankController {
    @Resource
    private ContribRankService contribRankService;

    @RequiresLogin
    @GetMapping()
    public ResponseBean getRankList() {
        List<UserContribPoint> rankList = contribRankService.getRankList();
        return new ResponseBean(200, "succ", rankList, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean getMyRank() throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        UserContribPoint userContribPoint = contribRankService.getUserContribPointByPhone(phone);
        return new ResponseBean(200, "succ", userContribPoint, TimeUtil.getFormattedTime(new Date()));
    }
}
