package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 资源评分DTO
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceScoreDto {

    /**
     * 资源评分
     */
    @DecimalMax(value = "5.0", message = "评分不能高于5分！")
    @DecimalMin(value = "0.0", message = "评分不能低于0分！")
    @NotNull(message = "评分不能为空！")
    private BigDecimal score;

    /**
     * 评论
     */
    private String comment;
}
