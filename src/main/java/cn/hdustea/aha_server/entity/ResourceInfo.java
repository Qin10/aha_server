package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class ResourceInfo {
    /**
     * 资源表id（外键）
     */
    private Integer resId;

    /**
     * 项目具体介绍
     */
    private String intro;

    private List<ResourceMember> members;
}