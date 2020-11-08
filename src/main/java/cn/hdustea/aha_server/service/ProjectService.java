package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.mapper.ProjectInfoMapper;
import cn.hdustea.aha_server.mapper.ProjectMapper;
import cn.hdustea.aha_server.mapper.ProjectMemberMapper;
import cn.hdustea.aha_server.mapper.UserCollectionMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * 项目服务类
 *
 * @author STEA_YY
 **/
@Service
public class ProjectService {
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ProjectInfoMapper projectInfoMapper;
    @Resource
    private ProjectMemberMapper projectMemberMapper;
    @Resource
    private UserCollectionMapper userCollectionMapper;
    @Resource
    private UserService userService;

    public List<Project> getAllProject() {
        return projectMapper.selectAll();
    }

    public Project getProjectById(int id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    public void saveProject(Project project) {
        projectMapper.insertSelective(project);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveProjectAndAuthor(Project project, String phone) {
        project.setCreatorPhone(phone);
        projectMapper.insertSelective(project);
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(project.getId());
        projectInfoMapper.insertSelective(projectInfo);
    }

    public void updateProjectByProjectId(Project project, int projectId) {
        project.setId(projectId);
        projectMapper.updateByPrimaryKeySelective(project);
    }

    public boolean hasPermission(String phone, int id) {
        Project project = projectMapper.selectByPrimaryKey(id);
        if (project != null && project.getCreatorPhone().equals(phone)) {
            return true;
        } else {
            ProjectMember projectMember = projectMemberMapper.selectByProjectIdAndMemberPhoneAndEditable(id, phone, true);
            return projectMember != null;
        }
    }

    public void deleteProjectById(int id) {
        projectMapper.deleteByPrimaryKey(id);
    }

//    public String signDownloadResourceByid(int id) throws SelectException {
//        Resource resource = resourceMapper.selectByPrimaryKey(id);
//        if (resource == null) {
//            throw new SelectException("不存在对应记录！");
//        }
//        if (resource.getFilename() == null) {
//            throw new SelectException("资源文件为空！");
//        }
//        URL url = ossService.signDownload(resource.getFilename());
//        return url.toString();
//    }

    public List<UserCollection> getAllCollectionByPhone(String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        return userCollectionMapper.selectAllByUserId(user.getId());
    }

    public void saveCollection(int projectId, String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        UserCollection userCollection = new UserCollection();
        userCollection.setProjectId(projectId);
        userCollection.setUserId(user.getId());
        userCollection.setTimestamp(new Date());
        try {
            userCollectionMapper.insert(userCollection);
        } catch (DuplicateKeyException ignored) {
        }
    }

    public void deleteCollection(int projectId, String phone) throws SelectException {
        User user = userService.getUserByPhone(phone);
        userCollectionMapper.deleteByPrimaryKey(user.getId(), projectId);
    }
}
