package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class VipLevel {
    private Integer id;

    /**
    * VIP等级名称
    */
    private String name;

    /**
    * 每周发放贡献点
    */
    private BigDecimal weeklyContribPoint;
}