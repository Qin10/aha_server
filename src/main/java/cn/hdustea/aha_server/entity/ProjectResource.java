package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.math.BigDecimal;

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
     * 资源类型
     */
    private Integer type;

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
     * 资源是否被冻结
     */
    private Boolean freezed;

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

    /**
     * 是否通过审核
     */
    private Boolean passed;
}