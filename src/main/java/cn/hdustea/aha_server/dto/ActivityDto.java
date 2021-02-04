package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动DTO
 *
 * @author STEA_YY
 **/
@Data
public class ActivityDto {
    /**
     * 活动标题
     */
    @NotEmpty(message = "活动标题不能为空！")
    private String title;

    /**
     * 活动介绍
     */
    @NotEmpty(message = "活动介绍不能为空！")
    private String intro;

    /**
     * 活动开始时间
     */
    @NotNull(message = "活动开始时间不能为空！")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @NotNull(message = "活动结束时间不能为空！")
    private Date endTime;

    /**
     * 兑换aha点数额
     */
    @NotNull(message = "活动结束时间不能为空！")
    private BigDecimal exchangeAhaPoint;

    /**
     * 兑换aha币数额
     */
    @NotNull(message = "aha币数额不能为空！")
    private BigDecimal exchangeAhaCredit;

    /**
     * 活动最大允许兑换券数量
     */
    @NotNull(message = "最大允许兑换券数量不能为空！")
    private Integer codeSum;
}
