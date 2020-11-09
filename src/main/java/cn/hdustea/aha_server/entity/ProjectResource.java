package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * ${description}
 *
 * @author STEA_YY
 **/

/**
 * 团队资源表，一个团队对应多个资源，资源表包括了资源文件名称，类别，阅读量等
 */
@Data
public class ProjectResource {

    /**
     * 项目资源id
     */
    private Integer id;

    /**
     * 团队id(外键)
     */
    @JsonIgnore
    private Integer projectId;

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
    private String filename;

    /**
     * 资源文件下载量
     */
    private Integer download;
}