package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户收藏实体类
 *
 * @author STEA_YY
 **/
@Data
public class UserCollection {
    /**
     * 收藏用户手机号
     */
    private String userPhone;

    /**
     * 收藏资源id
     */
    private Integer projectId;

    /**
     * 收藏时间戳
     */
    private Date timestamp;
}