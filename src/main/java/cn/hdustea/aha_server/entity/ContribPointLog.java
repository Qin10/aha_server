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
public class ContribPointLog {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 来源
     */
    private String source;

    /**
     * Aha币总额
     */
    private BigDecimal ahaCreditAmount;

    /**
     * Aha点总额
     */
    private BigDecimal ahaPointAmount;

    /**
     * 发生时间
     */
    private Date time;
}