package cn.hdustea.aha_server.entity;

import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class Competition {
    private Integer id;

    /**
     * 竞赛类别id(外键)
     */
    private Integer typeId;

    /**
     * 竞赛级别
     */
    private Integer level;

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