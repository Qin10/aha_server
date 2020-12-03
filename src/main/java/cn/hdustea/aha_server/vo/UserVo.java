package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.entity.Role;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户私有信息VO
 *
 * @author STEA_YY
 **/
@Data
public class UserVo {
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
     * 用户角色
     */
    private Role role;
}