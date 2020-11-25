package cn.hdustea.aha_server.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 项目资源Vo
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceVo {

    /**
     * 项目资源id
     */
    private Integer id;

    /**
     * 资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)
     */
    private String name;

    /**
     * 保存在oss里的资源文件名(包括前缀)
     */
    private String filename;

    /**
     * 保存在oss里的预览文件地址
     */
    private String previewUrl;

    /**
     * 资源文件下载量
     */
    private Integer download;
}