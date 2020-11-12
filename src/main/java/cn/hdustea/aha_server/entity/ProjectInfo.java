package cn.hdustea.aha_server.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 项目详细信息实体类
 *
 * @author STEA_YY
 **/

/**
 * 团队的详细信息（其实主要是富文本介绍）
 */
@Data
public class ProjectInfo {
    /**
     * 项目id(外键)
     */
    private Integer projectId;

    private Project project;

    /**
     * 赛事id(外键)
     */
    private Integer compId;

    private Competition competition;

    /**
     * 比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)
     */
    private String awardName;

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
    @JsonIgnore
    private String awardProveUrl;

    /**
     * 团队介绍(富文本)
     */
    private String intro;

    private List<ProjectMember> members;
    private List<ProjectResource> resources;
}