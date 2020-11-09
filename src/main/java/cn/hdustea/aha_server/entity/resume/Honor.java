package cn.hdustea.aha_server.entity.resume;

import lombok.Data;

/**
 * 用户简历中的荣誉信息实体类
 *
 * @author STEA_YY
 **/
@Data
public class Honor {
    /**
     * 荣誉名称
     */
    private String name;
    /**
     * 获得时间
     */
    private String time;
    /**
     * 说明
     */
    private String description;
}
