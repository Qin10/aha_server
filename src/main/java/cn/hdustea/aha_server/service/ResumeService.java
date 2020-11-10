package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.ResumeDao;
import cn.hdustea.aha_server.entity.Resume;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户简历的服务类
 *
 * @author STEA_YY
 **/
@Service
public class ResumeService {
    @Resource
    private ResumeDao resumeDao;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserService userService;

    /**
     * 根据id查询简历
     *
     * @param id 简历id
     * @return 简历实体类
     */
    public Resume getResumeById(ObjectId id) {
        return resumeDao.findById(id).get();
    }

    /**
     * 根据手机号查询简历
     *
     * @param phone 用户手机号
     * @return 简历实体类
     * @throws SelectException 查询失败异常
     */
    public Resume getResumeByPhone(String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        return resumeDao.findByUserId(user.getId());
    }

    /**
     * 新建简历
     *
     * @param resume 简历实体类
     * @return 增加了id字段的简历实体类
     */
    public Resume saveResume(Resume resume) {
        resume.setId(null);
        return resumeDao.save(resume);
    }

    /**
     * 修改简历
     *
     * @param resume 简历实体类
     */
    public void updateResume(Resume resume) {
        resumeDao.save(resume);
    }

    /**
     * 根据用户手机号修改简历
     *
     * @param resume 简历实体类
     * @param phone  用户手机号
     * @throws SelectException 用户不存在异常
     */
    public void updateResumeByPhone(Resume resume, String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        resume.setUserId(user.getId());
        Resume possibleResume = resumeDao.findByUserId(user.getId());
        if (possibleResume != null) {
            resume.setId(possibleResume.getId());
        }
        resumeDao.save(resume);
    }

    /**
     * 根据用户id删除简历
     *
     * @param userId 用户id
     */
    public void deleteResumeByUserId(int userId) {
        resumeDao.deleteByUserId(userId);
    }
}