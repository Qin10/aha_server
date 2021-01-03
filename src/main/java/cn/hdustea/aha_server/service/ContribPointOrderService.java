package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.constants.ContribPointLogConstants;
import cn.hdustea.aha_server.constants.ContribPointOrderConstants;
import cn.hdustea.aha_server.controller.ContribPointController;
import cn.hdustea.aha_server.dto.ContribPointOrderResourcesDto;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.mapper.ContribPointOrderMapper;
import cn.hdustea.aha_server.mapper.OrderProjectResourceMapper;
import cn.hdustea.aha_server.mapper.PurchasedResourceMapper;
import cn.hdustea.aha_server.vo.ContribPointOrderVo;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    private ContribPointLogService contribPointLogService;
    @Resource
    private UserService userService;
    @Resource
    private ProjectService projectService;
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
    public PageVo<List<ContribPointOrderVo>> getAllContribPointOrderVoByUserId(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("cor_create_time desc");
        List<ContribPointOrderVo> contribPointOrderVos = contribPointOrderMapper.selectAllVoByUserId(userId);
        PageInfo<ContribPointOrderVo> pageInfo = new PageInfo<>(contribPointOrderVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
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
//        if (projectService.isMember(contribPointOrderResourcesDto.getProjectId(), userId)) {
//            throw new InsertException("项目成员不可以购买项目资源！");
//        }
        BigDecimal totalCost = BigDecimal.valueOf(0.0);
        ContribPointOrder contribPointOrder = new ContribPointOrder();
        contribPointOrder.setCreateTime(new Date());
        contribPointOrder.setStatus(0);
        contribPointOrder.setUserId(userId);
        contribPointOrder.setProjectId(contribPointOrderResourcesDto.getProjectId());
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
            totalCost = totalCost.add(orderProjectResource.getPrice().multiply(BigDecimal.valueOf(1).subtract(orderProjectResource.getDiscount())));
        }
        contribPointOrder.setTotalCost(totalCost);
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
    public void payOrder(int userId, int orderId) throws UpdateException, SelectException {
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
        Date payTime = new Date();
        ContribPointLog contribPointLog = new ContribPointLog();
        if (userVo.getAhaPoint().compareTo(contribPointOrder.getTotalCost()) >= 0) {
            userService.updateDescAhaPoint(userId, contribPointOrder.getTotalCost());
            contribPointLog.setAhaPointAmount(contribPointOrder.getTotalCost().negate());
            contribPointOrder.setChargedAhaPoint(contribPointOrder.getTotalCost());
            contribPointOrder.setChargedAhaCredit(BigDecimal.ZERO);
        } else {
            if (userVo.getAhaCredit().add(userVo.getAhaPoint()).compareTo(contribPointOrder.getTotalCost()) >= 0) {
                BigDecimal ahaCreditAmount = contribPointOrder.getTotalCost().subtract(userVo.getAhaPoint());
                BigDecimal ahaPointAmount = userVo.getAhaPoint();
                userService.updateDescAhaPoint(userId, ahaPointAmount);
                contribPointLog.setAhaPointAmount(ahaPointAmount.negate());
                contribPointOrder.setChargedAhaPoint(ahaPointAmount);
                userService.updateDescAhaCredit(userId, ahaCreditAmount);
                contribPointLog.setAhaCreditAmount(ahaCreditAmount.negate());
                contribPointOrder.setChargedAhaCredit(ahaCreditAmount);
                payOffPercentage(contribPointOrder.getProjectId(), ahaCreditAmount.multiply(ContribPointOrderConstants.PERCENTAGE_RATE));
            } else {
                throw new UpdateException("用户余额不足！");
            }
        }
        contribPointLog.setUserId(userId);
        contribPointLog.setSource(ContribPointLogConstants.FROM_PAY_RESOURCE);
        contribPointLog.setTime(payTime);
        contribPointLogService.saveContribPointLog(contribPointLog);
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
            log.info(userOperationLogConfig.getFormat(), ContribPointController.MODULE_NAME, "购买资源", "id=" + orderProjectResource.getResourceId());
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
    public void operateOrder(int userId, int orderId, String action) throws UpdateException, SelectException {
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

    private void payOffPercentage(int projectId, BigDecimal ahaPointAmount) throws SelectException {
        Project project = projectService.getProjectById(projectId);
        userService.updateIncAhaPoint(project.getCreatorUserId(), ahaPointAmount);
        ContribPointLog contribPointLog = new ContribPointLog();
        contribPointLog.setUserId(project.getCreatorUserId());
        contribPointLog.setSource(ContribPointLogConstants.FROM_PERCENTAGE);
        contribPointLog.setAhaPointAmount(ahaPointAmount);
        contribPointLog.setTime(new Date());
        contribPointLogService.saveContribPointLog(contribPointLog);
    }
}
