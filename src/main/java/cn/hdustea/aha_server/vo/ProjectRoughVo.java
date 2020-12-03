package cn.hdustea.aha_server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 项目粗略信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class ProjectRoughVo {
    /**
     * 项目id
     */
    private Integer id;

    private UserRoughInfoVo creatorUser;

    /**
     * 项目名称
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
     * 团队头像url
     */
    private String avatarUrl;

    /**
     * 项目标签
     */
    private String tags;

    /**
     * 赛事id(外键)
     */
    private Integer compId;

    /**
     * 项目获奖级别
     */
    private Integer awardLevel;

    /**
     * 项目获奖时间
     */
    @JsonFormat(pattern = "yyyy-MM")
    private Date awardTime;
}