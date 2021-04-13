package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户粗略信息VO
 *
 * @author STEA_YY
 **/
@Data
public class UserRoughInfoVo {

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
     * 用户头像文件保存路径
     */
    private String avatarUrl;

    /**
     * 用户学校
     */
    private String school;

    /**
     * 用户VIP等级(外键)
     */
    private Integer vipLevelId;
}