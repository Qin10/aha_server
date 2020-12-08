package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class OrderProjectResource {
    /**
    * 订单号
    */
    private Integer orderId;

    /**
    * 项目资源id
    */
    private Integer resourceId;

    /**
    * 资源折扣
    */
    private BigDecimal discount;

    /**
    * 贡献点小计
    */
    private BigDecimal price;
}