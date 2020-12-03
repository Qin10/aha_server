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
     * 根据用户id查询简历
     *
     * @param userId 用户id
     * @return 简历实体类
     */
    public Resume getResumeByUserId(Integer userId) {
        Resume resume = resumeDao.findByUserId(userId);
        return resume == null ? new Resume() : resume;
    }

    /**
     * 根据用户id修改简历
     *
     * @param resume 简历实体类
     * @param userId  用户id
     * @throws SelectException 用户不存在异常
     */
    public void updateResumeByUserId(Resume resume, Integer userId) throws SelectException {
        userService.getExistUserVoById(userId);
        resume.setUserId(userId);
        Resume possibleResume = resumeDao.findByUserId(userId);
        if (possibleResume != null) {
            resume.setId(possibleResume.getId());
        }
        resumeDao.save(resume);
    }
}