package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceScore {
    /**
     * 用户id
     */
    private Integer userId;

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

    /**
     * 评价时间
     */
    private Date time;
}