package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserContribPoint {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 贡献点
     */
    private BigDecimal contribPoint;
    /**
     * 排名
     */
    private long rank;
}
