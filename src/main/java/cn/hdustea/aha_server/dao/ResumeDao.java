package cn.hdustea.aha_server.dao;

import cn.hdustea.aha_server.entity.Resume;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户简历的数据库操作类
 *
 * @author STEA_YY
 **/
@Repository
public interface ResumeDao extends MongoRepository<Resume, ObjectId> {
    Resume findByUserId(Integer userId);

    @SuppressWarnings("unused")
    void deleteByUserId(Integer userId);
}
