package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户公有信息表
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class UserInfo {
    /**
     * 用户id(外键)
     */
    private Integer userId;

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

    /**
     * 用户VIP等级(外键)
     */
    private Integer vipLevelId;
}