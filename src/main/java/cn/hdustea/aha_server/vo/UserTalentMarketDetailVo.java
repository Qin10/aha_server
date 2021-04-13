package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.entity.UserInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/4/6
 * @description: 人才市场详细信息VO
 */
@Data
public class UserTalentMarketDetailVo{

    /**
     * 用户id
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
     * 用户授权信息
     */
    private List<OauthVo> oauths;

    /**
     * 用户详细信息
     */
    private UserInfo userInfo;
}
