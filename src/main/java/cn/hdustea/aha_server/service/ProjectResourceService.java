package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.Project;
import cn.hdustea.aha_server.entity.ProjectResource;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ProjectResourceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.util.List;

/**
 * 项目资源服务类
 *
 * @author STEA_YY
 **/
@Service
public class ProjectResourceService {
    @Resource
    private ProjectResourceMapper projectResourceMapper;
    @Resource
    private OssService ossService;

    public List<ProjectResource> getAllProjectResourceByProjectId(int projectId) {
        return projectResourceMapper.selectAllByProjectId(projectId);
    }

    public ProjectResource getProjectResourceById(int id) {
        return projectResourceMapper.selectByPrimaryKey(id);
    }

    public String signDownloadProjectResourceByid(int id) throws SelectException {
        ProjectResource projectResource = projectResourceMapper.selectByPrimaryKey(id);
        if (projectResource == null) {
            throw new SelectException("不存在对应记录！");
        }
        if (projectResource.getFilename() == null) {
            throw new SelectException("资源文件为空！");
        }
        URL url = ossService.signDownload(projectResource.getFilename());
        return url.toString();
    }

    public void saveProjectResourceByProjectId(ProjectResource projectResource, int projectId) {
        projectResource.setProjectId(projectId);
        projectResourceMapper.insertSelective(projectResource);
    }

    public void updateProjectResourceById(ProjectResource projectResource, int id) {
        projectResource.setId(id);
        projectResourceMapper.updateByPrimaryKeySelective(projectResource);
    }

    public void deleteProjectResourceById(int id) {
        projectResourceMapper.deleteByPrimaryKey(id);
    }
}
