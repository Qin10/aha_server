package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class ContribPointOrder {
    /**
     * 订单号
     */
    private Integer id;

    /**
     * 用户id(外键)
     */
    private Integer userId;

    /**
     * 项目id(外键)
     */
    private Integer projectId;

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
     * 订单实际支付aha币
     */
    private BigDecimal chargedAhaCredit;

    /**
     * 订单实际支付aha点
     */
    private BigDecimal chargedAhaPoint;
}