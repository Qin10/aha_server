package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "成员手机号不能为空！")
    private String memberPhone;
    private String nickname;
    private String trueName;
    private String school;
    private String major;
    private String avatarUrl;
    /**
     * 成员职务
     */
    @NotEmpty(message = "成员职务不能为空！")
    private String job;
}