package cn.hdustea.aha_server.dto;

import lombok.Data;

/**
 * 实名认证信息DTO
 *
 * @author STEA_YY
 **/
@Data
public class RealNameAuthenticationDto {
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
}
