package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的校园经历实体类
 *
 * @author STEA_YY
 **/
@Data
public class SchoolExperience {
    /**
     * 社团/组织名
     */
    private String organization;
    /**
     * 职位
     */
    private String post;
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
