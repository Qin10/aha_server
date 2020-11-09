package cn.hdustea.aha_server.entity;

import cn.hdustea.aha_server.entity.valid.InsertGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 项目成员实体类
 *
 * @author STEA_YY
 **/

@Data
public class ProjectMember {
    /**
     * 项目id(外键)
     */
    @JsonIgnore
    private Integer projectId;

    /**
     * 团队成员手机号
     */
    @NotEmpty(message = "成员手机号不能为空")
    private String memberPhone;

    /**
     * 成员昵称
     */
    private String nickname;

    /**
     * 成员真实姓名
     */
    private String trueName;

    /**
     * 成员头像url
     */
    private String avatarUrl;

    /**
     * 成员所在学校
     */
    private String school;

    /**
     * 团队成员顺位(决定显示顺序，1为队长)
     */
    @NotNull(message = "成员顺位不能为空")
    private Integer rank;

    /**
     * 团队成员职务
     */
    @NotEmpty(message = "成员职务不能为空")
    private String job;

    /**
     * 成员是否可编辑项目信息
     */
    @NotNull(message = "成员编辑权限不能为空")
    private Boolean editable;
}