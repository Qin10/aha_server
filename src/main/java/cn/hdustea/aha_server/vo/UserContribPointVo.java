package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户贡献点及排名DTO
 */
@Data
public class UserContribPointVo {
    private UserRoughInfoVo user;
    /**
     * 贡献点
     */
    private BigDecimal contribPoint;
    /**
     * 排名
     */
    private long rank;
}
