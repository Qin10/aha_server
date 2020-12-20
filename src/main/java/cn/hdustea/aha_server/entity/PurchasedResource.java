package cn.hdustea.aha_server.entity;

import java.util.Date;

import lombok.Data;

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
     * 购买时间
     */
    private Date purchaseTime;
}