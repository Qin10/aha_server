package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/

/**
 * 团队成员表，一个团队对应多个成员，包括成员的手机号、顺位、角色、职位等信息
 */
@Data
public class ProjectMember {
    @JsonIgnore
    private Integer projectId;

    /**
     * 团队成员手机号
     */
    private String memberPhone;

    private String nickname;
    private String trueName;
    private String avatarUrl;
    private String school;

    /**
     * 团队成员顺位(决定显示顺序，1为队长)
     */
    private Integer rank;

    /**
     * 团队成员职务
     */
    private String job;

    /**
     * 成员是否可编辑项目信息
     */
    private Boolean editable;
}