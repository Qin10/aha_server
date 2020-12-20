package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 反馈用户端DTO
 *
 * @author STEA_YY
 **/
@Data
public class FeedbackUserDto {

    /**
     * 反馈类型
     */
    private Integer type;

    /**
     * 反馈内容
     */
    private String content;
}
