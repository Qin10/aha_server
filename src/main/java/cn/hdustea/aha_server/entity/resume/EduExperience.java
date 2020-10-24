package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的教育经历的实体类
 *
 * @author STEA_YY
 **/
@Data
public class EduExperience {
    private String degree;
    private String school;
    private String specialty;
    private String grade;
    private String startTime;
    private String endTime;
}
