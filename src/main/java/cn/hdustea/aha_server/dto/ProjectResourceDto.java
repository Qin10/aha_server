package cn.hdustea.aha_server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 项目资源dto
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceDto {

    /**
     * 资源文件类别
     */
    @NotNull(message = "资源类别不能为空")
    private Integer type;

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
}
