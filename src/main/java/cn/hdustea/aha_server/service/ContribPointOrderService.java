package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.constants.ContribPointLogConstants;
import cn.hdustea.aha_server.constants.ContribPointOrderConstants;
import cn.hdustea.aha_server.controller.ContribPointOrderController;
import cn.hdustea.aha_server.dto.ContribPointOrderResourcesDto;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.mapper.ContribPointLogMapper;
import cn.hdustea.aha_server.mapper.ContribPointOrderMapper;
import cn.hdustea.aha_server.mapper.OrderProjectResourceMapper;
import cn.hdustea.aha_server.mapper.PurchasedResourceMapper;
import cn.hdustea.aha_server.vo.ContribPointOrderVo;
import cn.hdustea.aha_server.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 贡献点订单服务类
 *
 * @author STEA_YY
 **/
@Service
@Slf4j(topic = "userOperationLog")
public class ContribPointOrderService {
    @Resource
    private ContribPointOrderMapper contribPointOrderMapper;
    @Resource
    private PurchasedResourceMapper purchasedResourceMapper;
    @Resource
    private OrderProjectResourceMapper orderProjectResourceMapper;
    @Resource
    private ContribPointLogMapper contribPointLogMapper;
    @Resource
    private UserService userService;
    @Resource
    private ProjectResourceService projectResourceService;
    @Resource
    private UserOperationLogConfig userOperationLogConfig;

    /**
     * 根据id获取贡献点订单VO
     *
     * @param id 贡献点订单表id
     * @return 贡献点订单VO
     */
    public ContribPointOrderVo getContribPointOrderVoById(int id) {
        return contribPointOrderMapper.selectVoByPrimaryKey(id);
    }

    /**
     * 根据用户id获取全部贡献点订单
     *
     * @param userId 用户id
     * @return 贡献点订单列表
     */
    public List<ContribPointOrderVo> getAllContribPointOrderVoByUserId(int userId) {
        return contribPointOrderMapper.selectAllVoByUserId(userId);
    }

    /**
     * 创建贡献点订单
     *
     * @param userId                        用户id
     * @param contribPointOrderResourcesDto 订购资源列表DTO
     * @return 贡献点订单id
     * @throws InsertException 插入异常
     */
    @Transactional(rollbackFor = {Exception.class})
    public int createOrder(int userId, ContribPointOrderResourcesDto contribPointOrderResourcesDto) throws InsertException {
        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        ContribPointOrder contribPointOrder = new ContribPointOrder();
        contribPointOrder.setCreateTime(new Date());
        contribPointOrder.setStatus(0);
        contribPointOrder.setUserId(userId);
        contribPointOrderMapper.insertSelective(contribPointOrder);
        for (Integer resourceId : contribPointOrderResourcesDto.getResourceIds()) {
            ProjectResource projectResource = projectResourceService.getProjectResourceById(resourceId);
            if (projectResource == null) {
                throw new InsertException("资源id=" + resourceId + "不存在！");
            }
            if (!projectResource.getProjectId().equals(contribPointOrderResourcesDto.getProjectId())) {
                throw new InsertException("资源id=" + resourceId + "不属于指定项目！");
            }
            if (purchasedResourceMapper.selectByUserIdAndResourceId(userId, resourceId) != null) {
                throw new InsertException("您已购买过该资源！");
            }
            OrderProjectResource orderProjectResource = new OrderProjectResource();
            orderProjectResource.setOrderId(contribPointOrder.getId());
            orderProjectResource.setResourceId(projectResource.getId());
            orderProjectResource.setPrice(projectResource.getPrice());
            orderProjectResource.setDiscount(projectResource.getDiscount());
            orderProjectResourceMapper.insertSelective(orderProjectResource);
            totalPrice = totalPrice.add(orderProjectResource.getPrice().multiply(BigDecimal.valueOf(1).subtract(orderProjectResource.getDiscount())));
        }

        contribPointOrder.setPrice(totalPrice);
        contribPointOrderMapper.updateByPrimaryKeySelective(contribPointOrder);
        return contribPointOrder.getId();
    }

    /**
     * 支付贡献点订单
     *
     * @param userId  用户id
     * @param orderId 贡献点订单id
     * @throws UpdateException 更新异常
     */
    @Transactional(rollbackFor = {Exception.class})
    public void payOrder(int userId, int orderId) throws UpdateException {
        UserVo userVo = userService.getUserVoById(userId);
        ContribPointOrder contribPointOrder = contribPointOrderMapper.selectByPrimaryKey(orderId);
        if (contribPointOrder == null) {
            throw new UpdateException("订单号错误！");
        }
        if (contribPointOrder.getUserId() != userId) {
            throw new UpdateException("非当前用户订单！");
        }
        if (contribPointOrder.getStatus() != ContribPointOrderConstants.STATUS_NOT_PAID) {
            throw new UpdateException("订单已支付或已被取消！");
        }
        if (userVo.getContribPoint().compareTo(contribPointOrder.getPrice()) < 0) {
            throw new UpdateException("用户贡献点余额不足！");
        }
        userService.updateDescContribPoint(userId, contribPointOrder.getPrice());
        Date payTime = new Date();
        ContribPointLog contribPointLog = new ContribPointLog();
        contribPointLog.setUserId(userId);
        contribPointLog.setSource(ContribPointLogConstants.FROM_PAY_RESOURCE);
        contribPointLog.setAmount(contribPointOrder.getPrice().negate());
        contribPointLog.setTime(payTime);
        contribPointLogMapper.insertSelective(contribPointLog);
        contribPointOrder.setStatus(ContribPointOrderConstants.STATUS_PAID);
        contribPointOrder.setPayTime(payTime);
        contribPointOrderMapper.updateByPrimaryKeySelective(contribPointOrder);
        List<OrderProjectResource> orderProjectResources = orderProjectResourceMapper.selectAllByOrderId(orderId);
        for (OrderProjectResource orderProjectResource : orderProjectResources) {
            PurchasedResource purchasedResource = new PurchasedResource();
            purchasedResource.setUserId(userId);
            purchasedResource.setResourceId(orderProjectResource.getResourceId());
            purchasedResource.setPurchaseTime(payTime);
            purchasedResourceMapper.insertSelective(purchasedResource);
            log.info(userOperationLogConfig.getFormat(), ContribPointOrderController.MODULE_NAME, "购买资源", "id=" + orderProjectResource.getResourceId());
        }
    }

    /**
     * 取消贡献点订单
     *
     * @param userId  用户id
     * @param orderId 贡献点订单id
     * @throws UpdateException 更新异常
     */
    @Transactional(rollbackFor = {Exception.class})
    public void cancelOrder(int userId, int orderId) throws UpdateException {
        ContribPointOrder contribPointOrder = contribPointOrderMapper.selectByPrimaryKey(orderId);
        if (contribPointOrder == null) {
            throw new UpdateException("订单号错误！");
        }
        if (contribPointOrder.getUserId() != userId) {
            throw new UpdateException("非当前用户订单！");
        }
        if (contribPointOrder.getStatus() != ContribPointOrderConstants.STATUS_NOT_PAID) {
            throw new UpdateException("订单已支付或已被取消！");
        }
        contribPointOrder.setStatus(ContribPointOrderConstants.STATUS_CANCELED);
        contribPointOrderMapper.updateByPrimaryKeySelective(contribPointOrder);
    }

    /**
     * 操作订单
     *
     * @param userId  用户id
     * @param orderId 贡献点订单id
     * @param action  操作(取值"pay","cancel")
     * @throws UpdateException 更新异常
     */
    public void operateOrder(int userId, int orderId, String action) throws UpdateException {
        switch (action) {
            case ContribPointOrderConstants.ACTION_PAY: {
                payOrder(userId, orderId);
                break;
            }
            case ContribPointOrderConstants.ACTION_CENCAL: {
                cancelOrder(userId, orderId);
                break;
            }
            default: {
                throw new UpdateException("'action'参数取值错误！");
            }
        }
    }
}