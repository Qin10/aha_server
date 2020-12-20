package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 反馈管理员端DTO
 *
 * @author STEA_YY
 **/
@Data
public class FeedbackAdminDto {

    /**
     * 反馈状态
     */
    private Integer status;

    /**
     * 反馈回复
     */
    private String reply;

    /**
     * 反馈问题级别
     */
    private Integer level;
}
