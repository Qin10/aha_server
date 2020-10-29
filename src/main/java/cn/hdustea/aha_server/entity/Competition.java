package cn.hdustea.aha_server.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * 竞赛信息的实体类
 *
 * @author STEA_YY
 **/
@Data
public class Competition {
    private Integer id;

    /**
     * 所属赛事标签（外键）
     */
    private Integer compTagId;

    private CompetitionTag competitionTag;

    /**
     * 赛事图片保存路径
     */
    private Integer picUrl;
}