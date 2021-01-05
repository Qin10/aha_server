package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 项目资源审核DTO
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceCheckDto {
    @NotNull(message = "是否通过审核不能为空")
    private Boolean passed;
}
