package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.ContribPointOrderResourcesDto;
import cn.hdustea.aha_server.entity.ContribPointLog;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.ContribPointLogService;
import cn.hdustea.aha_server.service.ContribPointOrderService;
import cn.hdustea.aha_server.service.ContributionRankService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ContribPointOrderVo;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 贡献点相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/contribPoint")
@Slf4j(topic = "userOperationLog")
public class ContribPointController {
    public static final String MODULE_NAME = "贡献点模块";
    @Resource
    private ContribPointOrderService contribPointOrderService;
    @Resource
    private ContributionRankService contributionRankService;
    @Resource
    private ContribPointLogService contribPointLogService;

    /**
     * 获取订单信息
     *
     * @param orderId 订单号
     */
    @RequiresLogin
    @GetMapping("/order/{orderId}")
    public ResponseBean<ContribPointOrderVo> getOrderVoById(@PathVariable("orderId") int orderId) {
        ContribPointOrderVo contribPointOrderVo = contribPointOrderService.getContribPointOrderVoById(orderId);
        return new ResponseBean<>(200, "succ", contribPointOrderVo);
    }

    /**
     * 获取用户全部订单信息
     *
     * @param pageNum  页码
     * @param pageSize 分页大小
     */
    @RequiresLogin
    @GetMapping("/order/me")
    public ResponseBean<PageVo<List<ContribPointOrderVo>>> getAllPersonalOrderVo(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        PageVo<List<ContribPointOrderVo>> ContribPointOrderVos = contribPointOrderService.getAllContribPointOrderVoByUserId(userId, pageNum, pageSize);
        return new ResponseBean<>(200, "succ", ContribPointOrderVos);
    }

    /**
     * 创建订单
     *
     * @param contribPointOrderResourcesDto 购买资源列表
     */
    @RequestLimit
    @RequiresLogin
    @PostMapping("/order")
    public ResponseBean<Integer> createOrder(@RequestBody @Validated ContribPointOrderResourcesDto contribPointOrderResourcesDto) throws InsertException, SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        int orderId = contribPointOrderService.createOrder(userId, contribPointOrderResourcesDto);
        return new ResponseBean<>(200, "succ", orderId);
    }

    /**
     * 对订单进行操作
     *
     * @param orderId 订单号
     * @param action  操作，取值pay、cancel
     */
    @RequiresLogin
    @PutMapping("/order/{orderId}")
    public ResponseBean<Object> operateOrder(@PathVariable("orderId") int orderId, @RequestParam("action") String action) throws UpdateException, SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        contribPointOrderService.operateOrder(userId, orderId, action);
        return new ResponseBean<>(200, "succ", orderId);
    }

    /**
     * 获取贡献点总排行榜
     *
     * @return 排行榜
     */
    @RequiresLogin
    @GetMapping("/rank")
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
    @GetMapping("/rank/me")
    public ResponseBean<UserContribPointVo> getMyRank() throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        UserContribPointVo userContribPointVo = contributionRankService.getUserContribPointByUserId(userId);
        return new ResponseBean<>(200, "succ", userContribPointVo);
    }

    /**
     * 获取用户个人贡献点变动日志
     *
     * @param pageNum  页码
     * @param pageSize 分页大小
     * @param type     类型
     * @param sortBy   排序关键字(取值time、type、contribPoint、ahaPoint、ahaCredit)
     * @param orderBy  排序方式(取值desc、asc，默认desc)
     */
    @RequiresLogin
    @GetMapping("/log/me")
    public ResponseBean<PageVo<List<ContribPointLog>>> getPersonalContribPointLog(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) Integer type, @RequestParam(required = false, defaultValue = "time") String sortBy, @RequestParam(required = false, defaultValue = "desc") String orderBy) throws SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        PageVo<List<ContribPointLog>> contribPointLogs = contribPointLogService.getAllContribPointLogPagableByUserIdAndConditions(pageNum, pageSize, userId, type, sortBy, orderBy);
        return new ResponseBean<>(200, "succ", contribPointLogs);
    }
}