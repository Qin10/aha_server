package cn.hdustea.aha_server.entity;

import cn.hdustea.aha_server.entity.resume.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 用户简历的实体类
 *
 * @author STEA_YY
 **/
@Document(collection = "resume")
@Data
public class Resume {
    /**
     * 简历id
     */
    @Id
    @JsonIgnore
    private ObjectId id;
    /**
     * 用户id(业务外键)
     */
    @JsonIgnore
    private int userId;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生年月
     */
    private String birth;
    /**
     * 最高学历
     */
    private String highestDegree;
    /**
     * 当前身份
     */
    private String identity;
    /**
     * 当前年级
     */
    private String currentGrade;
    /**
     * 期望工作地点
     */
    private String workPlace;
    /**
     * 期望职业
     */
    private String profession;
    /**
     * 教育经历列表
     */
    private List<EduExperience> eduExperiences;
    /**
     * 校内经历列表
     */
    private List<SchoolExperience> schoolExperiences;
    /**
     * 项目经历列表
     */
    private List<ProjectExperience> projectExperiences;
    /**
     * 实习经历列表
     */
    private List<PracticeExperience> practiceExperiences;
    /**
     * 项目技能
     */
    private String projectSkill;
    /**
     * 获得荣誉列表
     */
    private List<Honor> honors;
    /**
     * 个人介绍
     */
    private String intro;
}
