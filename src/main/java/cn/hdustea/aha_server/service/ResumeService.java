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

    public Resume getResumeById(ObjectId id) {
        return resumeDao.findById(id).get();
    }

    public Resume getResumeByPhone(String phone) throws SelectException {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo == null || userInfo.getResumeId() == null) {
            throw new SelectException();
        }
        return resumeDao.findById(new ObjectId(userInfo.getResumeId())).get();
    }

    public Resume saveResume(Resume resume) {
        resume.setId(null);
        return resumeDao.save(resume);
    }

    public void updateResume(Resume resume) {
        resumeDao.save(resume);
    }

    public void updateResumeByPhone(Resume resume, String phone) throws UpdateException {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo == null) {
            throw new UpdateException("用户不存在！");
        }
        if (userInfo.getResumeId() == null) {
            Resume savedResume = saveResume(resume);
            userInfoService.updateResumeIdByPhone(savedResume.getId().toString(), phone);
        } else {
            resume.setId(new ObjectId(userInfo.getResumeId()));
            updateResume(resume);
        }
    }

    public void deleteResumeById(ObjectId id) {
        resumeDao.deleteById(id);
    }
}