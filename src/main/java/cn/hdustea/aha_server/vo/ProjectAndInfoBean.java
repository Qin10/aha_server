package cn.hdustea.aha_server.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 创建项目时提供的粗略和详细信息封装
 *
 * @author STEA_YY
 **/
@Data
public class ProjectAndInfoBean {
    /**
     * 团队名称
     */
    @NotEmpty(message = "项目名称不能为空")
    private String name;

    /**
     * 团队头像url
     */
    private String avatarUrl;

    /**
     * 赛事id(外键)
     */
    @NotNull(message = "竞赛id不能为空")
    private Integer compId;

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
     * 团队介绍(富文本)
     */
    private String intro;
}
