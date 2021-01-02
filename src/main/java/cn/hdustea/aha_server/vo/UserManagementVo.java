package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.entity.Role;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户VO
 *
 * @author STEA_YY
 **/
@Data
public class UserManagementVo {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 账户首次登录时间
     */
    private Date createdTime;

    /**
     * 学生证图片保存路径
     */
    private String studentRecFilename;

    /**
     * aha币数量
     */
    private BigDecimal ahaCredit;

    /**
     * aha点数量
     */
    private BigDecimal ahaPoint;

    /**
     * 是否签署服务协议
     */
    private Boolean signedNotice;

    /**
     * 是否签署合同
     */
    private Boolean signedContract;

    /**
     * 角色id(外键)
     */
    private Integer roleId;

    /**
     * 角色信息
     */
    private Role role;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private Boolean gender;

    /**
     * 用户出生日期
     */
    private Date birthday;

    /**
     * 用户类别
     */
    private Integer typeId;

    /**
     * 用户个性签名
     */
    private String signature;

    /**
     * 用户头像文件保存路径
     */
    private String avatarUrl;

    /**
     * 用户学校
     */
    private String school;

    /**
     * 用户学院
     */
    private String academy;

    /**
     * 用户主修专业
     */
    private String major;

    /**
     * 用户当前年级
     */
    private Integer grade;

    /**
     * 用户自我介绍
     */
    private String intro;

    /**
     * 用户特长标签
     */
    private String specialtyTags;

    /**
     * 用户真实姓名
     */
    private String trueName;

    /**
     * 用户参与过比赛标签
     */
    private String compTags;
}
