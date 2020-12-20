package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 反馈VO
 *
 * @author STEA_YY
 **/
@Data
public class FeedbackVo {

    private Integer id;

    /**
     * 反馈用户id
     */
    private UserRoughInfoVo user;

    /**
     * 反馈时间
     */
    private Date time;

    /**
     * 反馈类型
     */
    private Integer type;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈状态
     */
    private Integer status;

    /**
     * 反馈回复
     */
    private String reply;

    /**
     * 反馈回复时间
     */
    private Date replyTime;

    /**
     * 反馈问题级别
     */
    private Integer level;
}
