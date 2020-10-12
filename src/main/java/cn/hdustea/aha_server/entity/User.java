package cn.hdustea.aha_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户基础数据的实体类
 *
 * @author STEA_YY
 **/
@Data
public class User {
    @JsonIgnore
    private int id;
    private String phone;
    private String password;
    private Timestamp createdTime;
    private int roleId;
    private Role role;
}
