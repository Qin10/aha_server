package cn.hdustea.aha_server.vo;

import lombok.Data;

import java.util.Date;

/**
 * 实名认证信息VO
 *
 * @author STEA_YY
 **/
@Data
public class RealNameAuthenticationVo {
    /**
     * 用户
     */
    private UserRoughInfoVo user;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 认证类型(学生or社会人士)
     */
    private Integer type;

    /**
     * 学生证图片文件名
     */
    private String studentCardFilename;

    /**
     * 身份证正面文件名
     */
    private String idCardFrontFilename;

    /**
     * 身份证背面文件名
     */
    private String idCardBackFilename;

    /**
     * 认证审核状态
     */
    private Integer status;

    /**
     * 上传日期
     */
    private Date uploadTime;

    /**
     * 审核通过日期
     */
    private Date passTime;
}
