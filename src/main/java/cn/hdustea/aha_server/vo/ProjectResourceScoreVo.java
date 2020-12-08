package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 资源评分VO
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceScoreVo {
    /**
     * 评价用户
     */
    private UserRoughInfoVo user;

    /**
     * 项目资源id
     */
    private Integer resourceId;

    /**
     * 资源评分
     */
    private BigDecimal score;

    /**
     * 评论
     */
    private String comment;
}
