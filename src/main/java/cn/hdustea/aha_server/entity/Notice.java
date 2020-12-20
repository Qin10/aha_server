package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class Notice {
    private Integer id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告开始日期
     */
    private Date createTime;

    /**
     * 公告投放开始日期
     */
    private Date puttingStartTime;

    /**
     * 公告投放结束日期
     */
    private Date puttingEndTime;

    /**
     * 公告是否启用
     */
    private Boolean enable;
}