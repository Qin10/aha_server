package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class ProjectResourceType {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 资源类型名称
    */
    private String name;

    /**
    * 资源价格系数
    */
    private BigDecimal priceCoefficient;
}