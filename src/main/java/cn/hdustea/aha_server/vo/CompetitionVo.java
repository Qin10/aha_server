package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.entity.CompetitionTag;
import lombok.Data;

/**
 * 竞赛信息Vo
 *
 * @author STEA_YY
 **/
@Data
public class CompetitionVo {
    /**
     * 竞赛id
     */
    private Integer id;

    /**
     * 所属赛事标签（外键）
     */
    private Integer compTagId;

    private CompetitionTag competitionTag;

    /**
     * 赛事名称
     */
    private String name;

    /**
     * 赛事简介
     */
    private String intro;

    /**
     * 赛事图片保存路径
     */
    private Integer picUrl;
}