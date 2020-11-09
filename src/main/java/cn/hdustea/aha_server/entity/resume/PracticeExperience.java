package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的实习经历实体类
 *
 * @author STEA_YY
 **/
@Data
public class PracticeExperience {
    /**
     * 公司名称
     */
    private String company;
    /**
     * 实习职位
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
