package cn.hdustea.aha_server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
     * 购买时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseTime;
}
