package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 轮播图资源DTO
 *
 * @author STEA_YY
 **/
@Data
public class SlideShowResourceDto {

    /**
     * 轮播图图片url
     */
    @NotEmpty(message = "图片url不能为空！")
    private String pictureUrl;

    /**
     * 轮播图路由路径
     */
    private String routerLink;

    /**
     * 是否启用
     */
    @NotNull(message = "是否启用不能为空！")
    private Boolean enabled;
}
