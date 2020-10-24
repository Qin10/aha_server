package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的实习经历实体类
 *
 * @author STEA_YY
 **/
@Data
public class PracticeExperience {
    private String company;
    private String post;
    private String startTime;
    private String endTime;
    private String description;
}
