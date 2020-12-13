package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.math.BigDecimal;

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
     * 项目id(外键)
     */
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
     * 保存在oss里的预览文件地址
     */
    private String previewUrl;

    /**
     * 资源文件下载量
     */
    private Integer download;

    /**
     * 资源平均分
     */
    private BigDecimal score;

    /**
     * 评分人数
     */
    private Integer scoreCount;

    /**
     * 资源价格
     */
    private BigDecimal price;

    /**
     * 资源折扣
     */
    private BigDecimal discount;
}