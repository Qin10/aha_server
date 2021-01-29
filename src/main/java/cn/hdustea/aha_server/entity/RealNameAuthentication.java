package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class RealNameAuthentication {
    /**
     * 用户id
     */
    private Integer userId;

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