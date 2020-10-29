package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 资源的实体类
 *
 * @author STEA_YY
 **/
@Data
public class Resource {
    private Integer id;

    /**
     * 作者id（外键）
     */
    private Integer authorUserId;

    /**
     * 资源全称
     */
    @NotEmpty(message = "资源名称不能为空！")
    private String name;

    /**
     * 资源文件存储路径
     */
    private String filename;

    /**
     * 赛事id（外键）
     */
    private Integer compId;

    /**
     * 资源级别（国家一二三、省市校）
     */
    private String level;

    /**
     * 资源属性（ppt/详细文档等）
     */
    private Integer type;

    /**
     * 获奖时间
     */
    private Date date;

    /**
     * 阅读量
     */
    private Integer read;

    /**
     * 收藏人数
     */
    private Integer collect;

    /**
     * 资源完整程度/意义大小（资源所需贡献点=级别分数*意义）
     */
    private BigDecimal meaning;

    /**
     * 项目头像路径
     */
    private String avatarUrl;
}