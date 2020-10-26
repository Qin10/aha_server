package cn.hdustea.aha_server.entity;

import cn.hdustea.aha_server.entity.resume.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
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
    @Id
    @JsonIgnore
    private ObjectId id;
    private String phone;
    private String email;
    private String gender;
    private String birth;
    private String hDegree;
    private String identity;
    private String currentGrade;
    private String workPlace;
    private String profession;
    private List<EduExperience> eduExperiences;
    private List<SchoolExperience> schoolExperiences;
    private List<ProjectExperience> projectExperiences;
    private List<PracticeExperience> practiceExperiences;
    private String projectSkill;
    private List<Honor> honors;
    private String intro;
}
