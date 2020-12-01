package cn.hdustea.aha_server.vo;

import lombok.Data;

/**
 * 项目成员Vo
 *
 * @author STEA_YY
 **/
@Data
public class ProjectMemberVo {

    /**
     * 团队成员用户id
     */
    private Integer memberUserId;

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
