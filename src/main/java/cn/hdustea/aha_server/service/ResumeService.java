package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.ResumeDao;
import cn.hdustea.aha_server.entity.Resume;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
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
    private UserService userService;

    /**
     * 根据手机号查询简历
     *
     * @param phone 用户手机号
     * @return 简历实体类
     */
    public Resume getResumeByPhone(String phone) {
        Resume resume = resumeDao.findByUserPhone(phone);
        return resume == null ? new Resume() : resume;
    }

    /**
     * 根据用户手机号修改简历
     *
     * @param resume 简历实体类
     * @param phone  用户手机号
     * @throws SelectException 用户不存在异常
     */
    public void updateResumeByPhone(Resume resume, String phone) throws SelectException {
        userService.getExistUserByPhone(phone);
        resume.setUserPhone(phone);
        Resume possibleResume = resumeDao.findByUserPhone(phone);
        if (possibleResume != null) {
            resume.setId(possibleResume.getId());
        }
        resumeDao.save(resume);
    }
}