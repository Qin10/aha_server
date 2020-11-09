package cn.hdustea.aha_server.entity;

import lombok.Data;

/**
 * 竞赛标签实体类
 *
 * @author STEA_YY
 **/
@Data
public class CompetitionTag {
    /**
     * 竞赛标签id
     */
    private Integer id;

    /**
     * 竞赛标签名称
     */
    private String name;
}