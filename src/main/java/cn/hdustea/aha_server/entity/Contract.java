package cn.hdustea.aha_server.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yedaxia.apidocs.Ignore;
import lombok.Data;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
@Data
public class Contract {
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