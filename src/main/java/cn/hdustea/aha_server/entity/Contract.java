package cn.hdustea.aha_server.entity;

import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class Contract {
    private Integer id;

    /**
     * 用户id(外键)
     */
    private Integer userId;

    /**
     * 联系人
     */
    @NotEmpty(message = "联系人不能为空！")
    private String name;

    /**
     * 联系电话
     */
    @NotEmpty(message = "联系电话不能为空！")
    private String phone;

    /**
     * 联系微信
     */
    @NotEmpty(message = "联系微信不能为空！")
    private String vx;

    /**
     * 联系邮箱
     */
    @NotEmpty(message = "联系邮箱不能为空！")
    private String email;

    /**
     * 签名文件名
     */
    private String signatureFilename;

    /**
     * 合同签名时间
     */
    private Date signTime;
}