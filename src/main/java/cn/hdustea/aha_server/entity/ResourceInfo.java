package cn.hdustea.aha_server.entity;

import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class ResourceInfo {
    private Integer id;

    /**
     * 资源表id（外键）
     */
    private Integer resId;

    /**
     * 项目具体介绍图保存路径
     */
    private String introPicFilename;

    /**
     * 项目具体介绍
     */
    private String intro;

    /**
     * 项目成员
     */
    private String member;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 微信联系方式
     */
    private String vx;
}