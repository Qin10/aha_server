package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的校园经历实体类
 *
 * @author STEA_YY
 **/
@Data
public class SchoolExperience {
    private String organization;
    private String post;
    private String startTime;
    private String endTime;
    private String description;
}
