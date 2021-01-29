package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.entity.Role;
import cn.hdustea.aha_server.entity.UserInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 登录用户获取个人信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class PersonalUserInfoVo {
    /**
     * 是否签署服务协议
     */
    private boolean signedNotice;
    /**
     * 是否签署合同
     */
    private boolean signedContract;
    /**
     * 是否通过身份认证
     */
    private Boolean authenticated;
    /**
     * aha币数量
     */
    private BigDecimal ahaCredit;
    /**
     * aha点数量
     */
    private BigDecimal ahaPoint;
    /**
     * 用户角色
     */
    private Role role;
    /**
     * 用户授权信息
     */
    private List<OauthVo> oauths;
    /**
     * 用户详细信息
     */
    private UserInfo userInfo;
}
