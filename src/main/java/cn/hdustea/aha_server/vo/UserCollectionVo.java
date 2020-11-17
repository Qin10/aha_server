package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户收藏VO
 *
 * @author STEA_YY
 **/
@Data
public class UserCollectionVo {

    /**
     * 资源粗略信息
     */
    private ProjectRoughVo project;

    /**
     * 收藏时间戳
     */
    private Date timestamp;
}
