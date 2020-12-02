package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "贡献点不能为空")
    private BigDecimal meaning;

    /**
     * 是否通过审核
     */
    @NotNull(message = "是否通过审核不能为空")
    private Boolean passed;
}
