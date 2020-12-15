package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 项目资源审核DTO
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceCheckDto {

    /**
     * 资源价格
     */
    @NotNull(message = "资源价格不能为空")
    private BigDecimal price;

    /**
     * 资源折扣
     */
    @NotNull(message = "资源折扣不能为空")
    private BigDecimal discount;
}
