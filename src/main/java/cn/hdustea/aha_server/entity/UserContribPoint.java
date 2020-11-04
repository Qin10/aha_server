package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserContribPoint {
    private String phone;
    private BigDecimal contribPoint;
    private long rank;
}
