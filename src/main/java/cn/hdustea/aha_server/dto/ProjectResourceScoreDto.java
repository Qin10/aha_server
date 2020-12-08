package cn.hdustea.aha_server.dto;

import lombok.Data;

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
    private BigDecimal score;

    /**
     * 评论
     */
    private String comment;
}
