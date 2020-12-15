package cn.hdustea.aha_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 项目资源dto
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceDto {

    /**
     * 资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)
     */
    @NotEmpty(message = "资源名称不能为空")
    private String name;

    /**
     * 保存在oss里的资源文件名(包括前缀)
     */
    @NotEmpty(message = "文件名不能为空")
    private String filename;

    /**
     * 资源价格
     */
    @NotNull(message = "资源价格不能为空")
    private BigDecimal price;

    /**
     * 资源折扣
     */
    @NotNull(message = "资源折扣不能为空")
    private BigDecimal discount;
}
