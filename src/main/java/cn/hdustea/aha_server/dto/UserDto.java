package cn.hdustea.aha_server.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户DTO
 *
 * @author STEA_YY
 **/
@Data
public class UserDto {

    /**
     * 学生证图片保存路径
     */
    private String studentRecFilename;

    /**
     * 贡献点
     */
    private BigDecimal contribPoint;

    /**
     * 是否签署服务协议
     */
    private Boolean signedNotice;

    /**
     * 是否签署合同
     */
    private Boolean signedContract;

    /**
     * 角色id(外键)
     */
    private Integer roleId;
}
