package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class PurchasedResource {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 项目资源id
     */
    private Integer resourceId;

    /**
     * 订单表id
     */
    private Integer orderId;

    /**
     * 购买时间
     */
    private Date purchaseTime;
}