package cn.hdustea.aha_server.vo;

import lombok.Data;

/**
 * 项目成员Vo
 *
 * @author STEA_YY
 **/
@Data
public class ProjectMemberVo {

    private UserRoughInfoVo memberUser;
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
