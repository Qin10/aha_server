package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.ContribPointOrderResourcesDto;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.ContribPointOrderService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ContribPointOrderVo;
import cn.hdustea.aha_server.vo.ResponseBean;
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
public class ContribPointOrderController {
    @Resource
    private ContribPointOrderService contribPointOrderService;

    @RequiresLogin
    @GetMapping("/{orderId}")
    public ResponseBean<ContribPointOrderVo> getOrderVoById(@PathVariable("orderId") int orderId) {
        ContribPointOrderVo contribPointOrderVo = contribPointOrderService.getContribPointOrderVoById(orderId);
        return new ResponseBean<>(200, "succ", contribPointOrderVo);
    }

    @RequiresLogin
    @GetMapping("/me")
    public ResponseBean<List<ContribPointOrderVo>> getAllPersonalOrderVo() {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        List<ContribPointOrderVo> ContribPointOrderVos = contribPointOrderService.getAllContribPointOrderVoByUserId(userId);
        return new ResponseBean<>(200, "succ", ContribPointOrderVos);
    }

    @RequiresLogin
    @PostMapping()
    public ResponseBean<Integer> createOrder(@RequestBody @Validated ContribPointOrderResourcesDto contribPointOrderResourcesDto) throws InsertException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        int orderId = contribPointOrderService.createOrder(userId, contribPointOrderResourcesDto);
        return new ResponseBean<>(200, "succ", orderId);
    }

    @RequiresLogin
    @PutMapping("/{orderId}")
    public ResponseBean<Object> operateOrder(@PathVariable("orderId") int orderId, @RequestParam("action") String action) throws UpdateException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        contribPointOrderService.operateOrder(userId, orderId, action);
        return new ResponseBean<>(200, "succ", orderId);
    }
}