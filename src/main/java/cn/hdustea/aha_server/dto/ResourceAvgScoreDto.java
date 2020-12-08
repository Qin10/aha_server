package cn.hdustea.aha_server.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 资源平均分DTO
 *
 * @author STEA_YY
 **/
@Data
public class ResourceAvgScoreDto {
    private Integer resourceId;
    private BigDecimal score;
}
