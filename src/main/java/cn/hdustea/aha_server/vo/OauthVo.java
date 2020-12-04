package cn.hdustea.aha_server.vo;

import lombok.Data;

/**
 * 第三方授权信息VO
 *
 * @author STEA_YY
 **/
@Data
public class OauthVo {

    /**
     * 授权类型
     */
    private String oauthType;

    /**
     * 授权码
     */
    private String oauthId;
}
