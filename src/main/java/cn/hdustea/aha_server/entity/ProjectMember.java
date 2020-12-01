package cn.hdustea.aha_server.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
    private Integer projectId;

    /**
     * 团队成员id
     */
    @NotNull(message = "成员id不能为空！")
    private Integer memberUserId;

    /**
     * 团队成员顺位(决定显示顺序，1为队长)
     */
    @NotNull(message = "成员顺位不能为空！")
    private Integer rank;

    /**
     * 团队成员职务
     */
    @NotNull(message = "成员职务不能为空！")
    private String job;

    /**
     * 成员是否可编辑项目信息
     */
    @NotNull(message = "成员权限不能为空！")
    private Boolean editable;
}