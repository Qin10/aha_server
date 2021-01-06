package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户购买资源记录管理方VO
 *
 * @author STEA_YY
 **/
@Data
public class PurchasedResourceManagementVo {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 购买时间
     */
    private Date purchaseTime;
    /**
     * 订单
     */
    private ContribPointOrderVo contribPointOrder;
}
