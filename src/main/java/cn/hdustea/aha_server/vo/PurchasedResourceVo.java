package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 已购资源VO
 *
 * @author STEA_YY
 **/
@Data
public class PurchasedResourceVo {
    /**
     * 购买的资源
     */
    private ProjectResourceVo resource;

    /**
     * 订单表id
     */
    private Integer orderId;

    /**
     * 购买时间
     */
    private Date purchaseTime;
}
