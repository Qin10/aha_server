package cn.hdustea.aha_server.entity;

import java.util.Date;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class ProjectResourceCheckLog {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 被审核资源id
    */
    private Integer resourceId;

    /**
    * 操作者用户id
    */
    private Integer operateUserId;

    /**
    * 审核结果
    */
    private Integer checkStatus;

    /**
    * 操作时间
    */
    private Date operateTime;
}