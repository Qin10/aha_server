package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/

/**
 * 团队表
 */
@Data
public class Project {
    private Integer id;

    /**
     * 团队创建者手机号
     */
    private String creatorPhone;

    /**
     * 团队名称
     */
    private String name;

    /**
     * 点击率
     */
    private Integer read;

    /**
     * 收藏数量
     */
    private Integer collect;

    /**
     * 项目资源完整程度，决定贡献点
     */
    @JsonIgnore
    private BigDecimal meaning;

    /**
     * 团队头像url
     */
    private String avatarUrl;

    /**
     * 项目是否通过审核，公开共享
     */
    private Boolean passed;
}