package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.entity.Competition;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目详细信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class ProjectDetailVo {

    /**
     * 项目id
     */
    private Integer id;

    private UserRoughInfoVo creatorUser;

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

    private Competition competition;

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
    @JsonFormat(pattern = "yyyy-MM")
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

    private List<ProjectMemberVo> members;
    private List<ProjectResourceVo> resources;
}
