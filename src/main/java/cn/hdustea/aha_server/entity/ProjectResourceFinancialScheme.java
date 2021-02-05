package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class ProjectResourceFinancialScheme {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 获奖等级
    */
    private Integer awardLevel;

    /**
    * 资源定价上限
    */
    private BigDecimal priceUpperLimit;

    /**
    * 资源定价下限
    */
    private BigDecimal priceLowerLimit;
}