package cn.hdustea.aha_server.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class User {
    @JsonIgnore
    private Integer id;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户密码
     */
    @JsonIgnore
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
     * 贡献点
     */
    private BigDecimal contribPoint;

    /**
     * 角色id(外键)
     */
    private Integer roleId;
}