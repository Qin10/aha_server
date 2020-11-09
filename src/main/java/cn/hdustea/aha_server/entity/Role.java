package cn.hdustea.aha_server.entity;

import lombok.Data;

/**
 * 角色实体类
 *
 * @author STEA_YY
 **/
@Data
public class Role {
    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;
}