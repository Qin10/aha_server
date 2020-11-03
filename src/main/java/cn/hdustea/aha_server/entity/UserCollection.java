package cn.hdustea.aha_server.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

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
    @JsonIgnore
    private Integer userId;

    /**
     * 收藏资源id
     */
    @JsonIgnore
    private Integer resId;

    private Resource resource;

    /**
     * 收藏时间戳
     */
    private Date timestamp;
}