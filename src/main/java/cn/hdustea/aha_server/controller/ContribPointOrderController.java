package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.ContribPointOrderResourcesDto;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.ContribPointOrderService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ContribPointOrderVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 贡献点订单类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/order")
@Slf4j(topic = "userOperationLog")
public class ContribPointOrderController {
    public static final String MODULE_NAME = "贡献点订单模块";
    @Resource
    private ContribPointOrderService contribPointOrderService;

    /**
     * 获取订单信息
     *
     * @param orderId 订单号
     */
    @RequiresLogin
    @GetMapping("/{orderId}")
    public ResponseBean<ContribPointOrderVo> getOrderVoById(@PathVariable("orderId") int orderId) {
        ContribPointOrderVo contribPointOrderVo = contribPointOrderService.getContribPointOrderVoById(orderId);
        return new ResponseBean<>(200, "succ", contribPointOrderVo);
    }

    /**
     * 获取用户全部订单信息
     */
    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean<List<ContribPointOrderVo>> getAllPersonalOrderVo() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        List<ContribPointOrderVo> ContribPointOrderVos = contribPointOrderService.getAllContribPointOrderVoByUserId(userId);
        return new ResponseBean<>(200, "succ", ContribPointOrderVos);
    }

    /**
     * 创建订单
     *
     * @param contribPointOrderResourcesDto 购买资源列表
     */
    @RequiresLogin
    @PostMapping()
    public ResponseBean<Integer> createOrder(@RequestBody @Validated ContribPointOrderResourcesDto contribPointOrderResourcesDto) throws InsertException {
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
    @PutMapping("/{orderId}")
    public ResponseBean<Object> operateOrder(@PathVariable("orderId") int orderId, @RequestParam("action") String action) throws UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        contribPointOrderService.operateOrder(userId, orderId, action);
        return new ResponseBean<>(200, "succ", orderId);
    }
}