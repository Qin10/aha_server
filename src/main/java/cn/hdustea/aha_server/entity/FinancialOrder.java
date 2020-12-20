package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class FinancialOrder {
    /**
     * 财务订单号
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 支付渠道
     */
    private Byte payChannel;

    /**
     * 第三方支付流水号
     */
    private String payNumber;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 订单总价
     */
    private BigDecimal price;

    /**
     * 订单状态
     */
    private Byte status;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单支付时间
     */
    private Date payTime;
}