package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 带详细信息的用户实体类
 *
 * @author STEA_YY
 **/
@Data
@ToString(callSuper = true)
public class UserInfo {
    @JsonIgnore
    private int id;
    @JsonIgnore
    private int userId;
    private String nickname;
    private boolean gender;
    private Date birthday;
    private Integer typeId;
    private String signature;
    private String avatarFilename;
    private String school;
    private String academy;
    private String major;
    private Integer grade;
    private String intro;
    private String specialtyTags;
    private String trueName;
    private String compTags;
    private Integer resumeId;
}
