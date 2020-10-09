package cn.hdustea.aha_server.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @program: aha_server
 * @description: 用户基础数据的实体类
 * @author: HduStea_YY
 * @create: 2020-10-09 10:42
 **/
@Data
public class User {
    private int id;
    private String username;
    private String phone;
    private String password;
    private Timestamp createdTime;
    private String avatarUrl;
    private int roleId;
    private Role role;
}
