package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 实名认证信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class RealNameAuthenticationDto {
    /**
     * 真实姓名
     */
    @NotEmpty(message = "真实姓名不能为空！")
    private String trueName;
    /**
     * 实名认证类型
     */
    @NotNull(message = "认证类型不能为空！")
    private Integer type;
}
