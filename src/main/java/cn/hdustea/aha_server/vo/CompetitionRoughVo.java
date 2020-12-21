package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.entity.CompetitionType;
import lombok.Data;

/**
 * 竞赛粗略信息VO
 *
 * @author STEA_YY
 */
@Data
public class CompetitionRoughVo {
    /**
     * 竞赛id
     */
    private Integer id;

    /**
     * 所属赛事类别
     */
    private CompetitionType competitionType;

    /**
     * 赛事名称
     */
    private String name;

    /**
     * 竞赛级别
     */
    private Integer level;

    /**
     * 赛事图片保存路径
     */
    private Integer picUrl;
}
