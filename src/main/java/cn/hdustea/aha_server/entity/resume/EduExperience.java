package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的教育经历的实体类
 *
 * @author STEA_YY
 **/
@Data
public class EduExperience {
    /**
     * 教育层次
     */
    private String degree;
    /**
     * 学校名称
     */
    private String school;
    /**
     * 专业
     */
    private String specialty;
    /**
     * 专业排名
     */
    private String grade;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
