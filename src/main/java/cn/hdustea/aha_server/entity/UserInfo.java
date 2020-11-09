package cn.hdustea.aha_server.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 用户详细信息实体类
 *
 * @author STEA_YY
 **/
@Data
public class UserInfo {
    /**
     * 用户主键(外键)
     */
    @JsonIgnore
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
}