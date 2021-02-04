package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class UserStatistics {
    /**
     * 主键
     */
    private Integer userId;

    /**
     * 累计获取贡献点
     */
    private BigDecimal totalContribPoint;

    /**
     * 累计参与项目数
     */
    private Integer totalProject;

    /**
     * 累计获得的收藏
     */
    private Integer totalReceivedCollection;

    /**
     * 累计被购买次数
     */
    private Integer totalPurchasedCount;
}