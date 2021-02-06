package cn.hdustea.aha_server.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户更新项目资源信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class ProjectResourceUpdateDto {
    /**
     * 资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)
     */
    private String name;

    /**
     * 资源文件类型
     */
    private Integer fileType;

    /**
     * 资源类型id(外键)
     */
    private Integer typeId;

    /**
     * 资源价格
     */
    private BigDecimal price;

    /**
     * 资源折扣
     */
    private BigDecimal discount;
}
