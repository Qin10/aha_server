package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的项目经历实体类
 *
 * @author STEA_YY
 **/
@Data
public class ProjectExperience {
    /**
     * 项目名称
     */
    private String name;
    /**
     * 担任职务
     */
    private String content;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 说明
     */
    private String description;
}
