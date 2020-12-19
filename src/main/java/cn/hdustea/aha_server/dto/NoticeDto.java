package cn.hdustea.aha_server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 全局通知DTO
 *
 * @author STEA_YY
 **/
@Data
public class NoticeDto {

    /**
     * 公告标题
     */
    @NotEmpty(message = "公告标题不能为空")
    private String title;

    /**
     * 公告内容
     */
    @NotEmpty(message = "公告内容不能为空")
    private String content;

    /**
     * 公告投放开始日期
     */
    @NotNull(message = "公告投放开始日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date puttingStartTime;

    /**
     * 公告投放结束日期
     */
    @NotNull(message = "公告投放结束日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date puttingEndTime;

    /**
     * 公告是否启用
     */
    @NotNull(message = "公告是否启用不能为空")
    private Boolean enable;
}
