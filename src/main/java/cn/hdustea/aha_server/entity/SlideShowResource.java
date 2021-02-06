package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class SlideShowResource {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 轮播图图片url
     */
    private String pictureUrl;

    /**
     * 轮播图路由路径
     */
    private String routerLink;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 轮播图上传时间
     */
    private Date uploadTime;
}