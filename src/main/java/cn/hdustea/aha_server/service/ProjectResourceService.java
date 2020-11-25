package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.DocumentConvertInfoDto;
import cn.hdustea.aha_server.dto.ProjectResourceDto;
import cn.hdustea.aha_server.entity.ProjectResource;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ProjectResourceMapper;
import cn.hdustea.aha_server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;

/**
 * 项目资源服务类
 *
 * @author STEA_YY
 **/
@Service
@Slf4j
public class ProjectResourceService {
    @Resource
    private ProjectResourceMapper projectResourceMapper;
    @Resource
    private OssService ossService;
    @Resource
    private RedisUtil redisUtil;

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
     * @param projectResourceDto 项目资源
     * @param projectId          项目id
     */
    public Integer saveProjectResourceByProjectId(ProjectResourceDto projectResourceDto, int projectId) {
        ProjectResource projectResource = new ProjectResource();
        BeanUtils.copyProperties(projectResourceDto, projectResource);
        projectResource.setProjectId(projectId);
        projectResourceMapper.insertSelective(projectResource);
        Integer projectResourceId = projectResource.getId();
        if (projectResourceId != null) {
            DocumentConvertInfoDto documentConvertInfoDto = new DocumentConvertInfoDto();
            documentConvertInfoDto.setProjectResourceId(projectResourceId);
            documentConvertInfoDto.setSrcFilename(projectResourceDto.getFilename());
            redisUtil.lPush(RedisUtil.DOCUMENT_CONVERT_LIST_KEY, documentConvertInfoDto);
        }
        return projectResourceId;
    }

    /**
     * 根据项目资源id更新项目资源
     *
     * @param projectResourceDto 更新的项目资源
     * @param id                 项目资源id
     */
    public void updateProjectResourceById(ProjectResourceDto projectResourceDto, int id) {
        ProjectResource projectResource = new ProjectResource();
        BeanUtils.copyProperties(projectResourceDto, projectResource);
        projectResource.setId(id);
        projectResourceMapper.updateByPrimaryKeySelective(projectResource);
    }

    /**
     * 根据项目资源id删除项目资源
     *
     * @param id 项目资源id
     */
    public void deleteProjectResourceById(int id) {
        projectResourceMapper.deleteByPrimaryKey(id);
    }

    public void incrDownloadById(int id) {
        projectResourceMapper.updateIncDownloadById(id);
    }

    public void freezeProjectResourceByFilename(String filename, boolean freezed) {
        ProjectResource projectResource = projectResourceMapper.selectByFilename(filename);
        if (projectResource != null) {
            log.info("projectId=" + projectResource.getProjectId() + ";projectResourceId=" + projectResource.getId() + "，资源冻结状态更改为：" + freezed);
            projectResourceMapper.updateFreezedByFilename(freezed, filename);
        }
    }
}
