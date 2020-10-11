package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @program: aha_server
 * @description: 带详细信息的用户实体类
 * @author: HduStea_YY
 * @create: 2020-10-09 17:04
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
    private String signature;
    private String avatarFilename;
}
