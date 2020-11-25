package cn.hdustea.aha_server.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目审核DTO
 *
 * @author STEA_YY
 **/
@Data
public class ProjectCheckDto {
    /**
     * 项目资源完整程度，决定贡献点
     */
    private BigDecimal meaning;

    /**
     * 是否通过审核
     */
    private Boolean passed;
}
