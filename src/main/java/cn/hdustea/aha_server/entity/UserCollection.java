package cn.hdustea.aha_server.entity;

import java.util.Date;

import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class UserCollection {
    /**
     * 收藏用户id
     */
    private Integer userId;

    /**
     * 收藏资源id
     */
    private Integer projectId;

    private Project project;

    /**
     * 收藏时间戳
     */
    private Date timestamp;
}