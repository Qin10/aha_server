package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.ResumeDao;
import cn.hdustea.aha_server.entity.Resume;
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
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo == null || userInfo.getResumeId() == null) {
            throw new SelectException();
        }
        return resumeDao.findById(new ObjectId(userInfo.getResumeId())).get();
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
     * @throws UpdateException 修改失败异常
     */
    public void updateResumeByPhone(Resume resume, String phone) throws SelectException {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo.getResumeId() == null) {
            Resume savedResume = saveResume(resume);
            userInfoService.updateResumeIdByPhone(savedResume.getId().toString(), phone);
        } else {
            resume.setId(new ObjectId(userInfo.getResumeId()));
            updateResume(resume);
        }
    }

    /**
     * 根据id删除简历
     *
     * @param id 简历id
     */
    public void deleteResumeById(ObjectId id) {
        resumeDao.deleteById(id);
    }
}