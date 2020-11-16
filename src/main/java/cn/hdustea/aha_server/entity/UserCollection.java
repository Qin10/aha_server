package cn.hdustea.aha_server.entity;

import java.util.Date;

import cn.hdustea.aha_server.vo.ProjectRoughVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 用户收藏实体类
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
    private Integer projectId;

    /**
     * 资源粗略信息
     */
    private ProjectRoughVo project;

    /**
     * 收藏时间戳
     */
    private Date timestamp;
}