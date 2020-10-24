package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的项目经历实体类
 *
 * @author STEA_YY
 **/
@Data
public class ProjectExperience {
    private String name;
    private String content;
    private String startTime;
    private String endTime;
    private String description;
}
