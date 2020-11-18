package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 项目资源实体类
 *
 * @author STEA_YY
 **/

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
     * 资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)
     */
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