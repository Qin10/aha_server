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
     * 用户id
     */
    private Integer userId;

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