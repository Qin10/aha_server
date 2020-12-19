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
    private UserRoughInfoVo user;
    private Date purchaseTime;
}
