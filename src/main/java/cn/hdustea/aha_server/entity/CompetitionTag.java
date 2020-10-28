package cn.hdustea.aha_server.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 竞赛标签的实体类
 *
 * @author STEA_YY
 **/
@Data
public class CompetitionTag {
    private Integer id;
    @NotEmpty(message = "竞赛标签名称不能为空！")
    private String name;
}