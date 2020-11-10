package cn.hdustea.aha_server.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 合同实体类
 *
 * @author STEA_YY
 **/
@Data
public class Contract {
    /**
     * 合同id
     */
    private Integer id;

    /**
     * 用户id(外键)
     */
    private Integer userId;

    /**
     * 联系人
     */
    private String name;

    /**
     * 签名文件名
     */
    private String signatureFilename;

    /**
     * 合同签名时间
     */
    private Date signTime;
}