package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class RewardFinancialScheme {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 奖励计划名称
    */
    private String name;

    /**
    * 奖励aha点数量
    */
    private BigDecimal ahaPointAmount;

    /**
    * 奖励aha币数量
    */
    private BigDecimal ahaCreditAmount;

    /**
    * 计划上传时间
    */
    private Date uploadTime;
}