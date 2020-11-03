package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class ResourceMember {
    /**
     * 资源id(外键)
     */
    @JsonIgnore
    private Integer resId;

    /**
     * 成员手机号(外键)
     */
    private String memberPhone;

    private String trueName;
    private String school;
    private String major;
    private String avatarUrl;
    /**
     * 成员职务
     */
    private String job;
}