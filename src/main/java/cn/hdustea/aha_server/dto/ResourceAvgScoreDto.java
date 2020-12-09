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

    /**
     * 项目资源id
     */
    private Integer resourceId;

    /**
     * 平均分
     */
    private BigDecimal score;
}
