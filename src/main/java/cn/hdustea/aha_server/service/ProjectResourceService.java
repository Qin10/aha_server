package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.Project;
import cn.hdustea.aha_server.entity.ProjectResource;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ProjectMapper;
import cn.hdustea.aha_server.mapper.ProjectResourceMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    /**
     * 根据项目资源id获取项目资源
     *
     * @param id 项目资源id
     * @return 项目资源
     */
    public ProjectResource getProjectResourceById(int id) {
        return projectResourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据项目资源id获取下载url
     *
     * @param id 项目资源id
     * @return 下载url
     * @throws SelectException 查询异常
     */
    public String signDownloadProjectResourceByid(int id) throws SelectException {
        ProjectResource projectResource = getProjectResourceById(id);
        if (projectResource == null) {
            throw new SelectException("不存在对应记录！");
        }
        if (projectResource.getFilename() == null) {
            throw new SelectException("资源文件为空！");
        }
        URL url = ossService.signDownload(projectResource.getFilename());
        return url.toString();
    }

    /**
     * 新增项目资源
     *
     * @param projectResource 项目资源
     * @param projectId       项目id
     */
    public void saveProjectResourceByProjectId(ProjectResource projectResource, int projectId) {
        projectResource.setProjectId(projectId);
        projectResourceMapper.insertSelective(projectResource);
    }

    /**
     * 根据项目资源id更新项目资源
     *
     * @param projectResource 更新的项目资源
     * @param id              项目资源id
     */
    public void updateProjectResourceById(ProjectResource projectResource, int id, int projectId) {
        projectResource.setId(id);
        projectResourceMapper.updateByPrimaryKeySelective(projectResource);
    }

    /**
     * 根据项目资源id删除项目资源
     *
     * @param id 项目资源id
     */
    public void deleteProjectResourceById(int id, int projectId) {
        projectResourceMapper.deleteByPrimaryKey(id);
    }
}
