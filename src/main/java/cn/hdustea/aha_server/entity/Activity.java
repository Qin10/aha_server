package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class Activity {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 创建者用户id
     */
    private Integer creatorUserId;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动介绍
     */
    private String intro;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 活动创建时间
     */
    private Date createTime;

    /**
     * 兑换aha点数额
     */
    private BigDecimal exchangeAhaPoint;

    /**
     * 兑换aha币数额
     */
    private BigDecimal exchangeAhaCredit;

    /**
     * 活动最大允许兑换券数量
     */
    private Integer codeSum;
}