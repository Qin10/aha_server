package cn.hdustea.aha_server.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户贡献点及排名DTO
 */
@Data
public class UserContribPointBean {
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