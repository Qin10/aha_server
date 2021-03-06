package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 团队表
 *
 * @author STEA_YY
 */
@Data
public class Project {
    private Integer id;

    /**
     * 团队创建者id
     */
    private Integer creatorUserId;

    /**
     * 团队名称
     */
    private String name;

    /**
     * 团队头像url
     */
    private String avatarUrl;

    /**
     * 项目标签
     */
    private String tags;

    /**
     * 团队介绍(富文本)
     */
    private String intro;

    /**
     * 点击率
     */
    private Integer read;

    /**
     * 收藏数量
     */
    private Integer collect;

    /**
     * 赛事id(外键)
     */
    private Integer compId;

    /**
     * 比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)
     */
    private String compName;

    /**
     * 项目获奖级别
     */
    private Integer awardLevel;

    /**
     * 项目获奖时间
     */
    private Date awardTime;

    /**
     * 获奖证明文件url
     */
    private String awardProveUrl;

    /**
     * 项目资源完整程度，决定贡献点
     */
    private BigDecimal meaning;

    /**
     * 是否通过审核
     */
    private Boolean passed;
}