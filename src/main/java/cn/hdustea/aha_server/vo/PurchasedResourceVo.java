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
    private ProjectResourceVo resource;
    private Date purchaseTime;
}
