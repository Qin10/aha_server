package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.dao.ResumeDao;
import cn.hdustea.aha_server.dao.UserMapper;
import cn.hdustea.aha_server.entity.Resume;
import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserInfo;
import cn.hdustea.aha_server.exception.apiException.authenticationException.UserNotFoundException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.util.TimeUtil;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

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
        resume.setId(new ObjectId());
        return resumeDao.save(resume);
    }

    public void updateResume(Resume resume) throws UpdateException {
        if (!resumeDao.existsById(resume.getId())) {
            throw new UpdateException("简历记录不存在！");
        }
        resumeDao.save(resume);
    }



    public void updateResumeByPhone(Resume resume, String phone) throws UpdateException {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        if (userInfo == null || userInfo.getResumeId() == null) {
            throw new UpdateException("简历记录不存在！");
        } else {
            resume.setId(new ObjectId(userInfo.getResumeId()));
            updateResume(resume);
        }
    }

    public void deleteResumeById(ObjectId id) {
        resumeDao.deleteById(id);
    }
}