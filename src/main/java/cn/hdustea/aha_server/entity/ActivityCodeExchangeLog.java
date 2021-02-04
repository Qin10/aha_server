package cn.hdustea.aha_server.entity;

import java.util.Date;
import lombok.Data;

/**
* ${description}
*
* @author STEA_YY
**/
@Data
public class ActivityCodeExchangeLog {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 兑换者id
    */
    private Integer userId;

    /**
    * 活动id
    */
    private Integer activityId;

    /**
    * 兑换码
    */
    private String code;

    /**
    * 兑换时间
    */
    private Date exchangeTime;
}