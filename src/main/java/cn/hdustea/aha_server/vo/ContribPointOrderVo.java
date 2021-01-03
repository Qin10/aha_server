package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 贡献点订单VO
 *
 * @author STEA_YY
 **/
@Data
public class ContribPointOrderVo {
    /**
     * 订单号
     */
    private Integer id;

    /**
     * 用户
     */
    private UserRoughInfoVo user;

    /**
     * 项目
     */
    private ProjectRoughVo project;

    /**
     * 订单总价
     */
    private BigDecimal totalCost;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单支付时间
     */
    private Date payTime;

    /**
     * 订单对应项目资源
     */
    private List<OrderProjectResourceVo> orderResources;

    /**
     * 订单实际支付aha币
     */
    private BigDecimal chargedAhaCredit;

    /**
     * 订单实际支付aha点
     */
    private BigDecimal chargedAhaPoint;
}
