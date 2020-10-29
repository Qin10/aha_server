package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源详细信息的实体类
 *
 * @author STEA_YY
 **/
@Data
public class ResourceInfo {
    @JsonIgnore
    private Integer id;

    /**
     * 资源表id（外键）
     */
    @NotNull(message = "资源id不能为空！")
    private Integer resId;

    /**
     * 项目具体介绍图保存路径
     */
    private String introPicUrl;

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