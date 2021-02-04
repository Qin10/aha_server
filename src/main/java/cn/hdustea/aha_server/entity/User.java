package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class User {
    private Integer id;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 账户首次登录时间
     */
    private Date createdTime;

    /**
     * 学生证图片保存路径
     */
    private String studentRecFilename;

    /**
     * aha币数量
     */
    private BigDecimal ahaCredit;

    /**
     * aha点数量
     */
    private BigDecimal ahaPoint;

    /**
     * 是否签署服务协议
     */
    private Boolean signedNotice;

    /**
     * 是否签署合同
     */
    private Boolean signedContract;

    /**
     * 是否通过身份认证
     */
    private Integer authenticated;

    /**
     * 角色id(外键)
     */
    private Integer roleId;
}