package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.entity.Role;
import cn.hdustea.aha_server.entity.UserInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 登录用户获取个人信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class PersonalUserInfoVo {
    /**
     * 手机号
     */
    private String phone;
    /**
     * 是否签署服务协议
     */
    private boolean signedNotice;
    /**
     * 是否签署合同
     */
    private boolean signedContract;
    /**
     * 贡献点
     */
    private BigDecimal contribPoint;
    /**
     * 用户角色
     */
    private Role role;
    /**
     * 用户详细信息
     */
    private UserInfo userInfo;
}
