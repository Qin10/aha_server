package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.dto.*;
import cn.hdustea.aha_server.entity.ProjectResource;
import cn.hdustea.aha_server.entity.ProjectResourceScore;
import cn.hdustea.aha_server.entity.PurchasedResource;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.mapper.ProjectResourceMapper;
import cn.hdustea.aha_server.mapper.ProjectResourceScoreMapper;
import cn.hdustea.aha_server.mapper.PurchasedResourceMapper;
import cn.hdustea.aha_server.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

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
    private ProjectResourceScoreMapper projectResourceScoreMapper;
    @Resource
    private PurchasedResourceMapper purchasedResourceMapper;
    @Resource
    private OssService ossService;
    @Resource
    private ProjectService projectService;
    @Resource
    private RedisService redisService;

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
     * 根据项目资源id获取项目资源VO
     *
     * @param id 项目资源id
     * @return 项目资源
     */
    public ProjectResourceVo getProjectResourceVoById(int id) {
        return projectResourceMapper.selectVoByPrimaryKey(id);
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
            redisService.lPush(RedisConstants.DOCUMENT_CONVERT_LIST_KEY, documentConvertInfoDto);
        }
        return projectResourceId;
    }

    /**
     * 根据项目资源id更新项目资源
     *
     * @param projectResourceUpdateDto 更新的项目资源
     * @param id                       项目资源id
     */
    public void updateProjectResourceById(ProjectResourceUpdateDto projectResourceUpdateDto, int id) {
        ProjectResource projectResource = new ProjectResource();
        BeanUtils.copyProperties(projectResourceUpdateDto, projectResource);
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

    /**
     * 根据项目资源id增加下载量
     *
     * @param id 项目资源id
     */
    public void incrDownloadById(int id) {
        projectResourceMapper.updateIncDownloadById(id);
    }

    /**
     * 根据文件名改变项目资源冻结状态
     *
     * @param filename 文件名
     * @param freezed  资源冻结状态
     */
    public void freezeProjectResourceByFilename(String filename, boolean freezed) {
        ProjectResource projectResource = projectResourceMapper.selectByFilename(filename);
        if (projectResource != null) {
            log.info("projectId=" + projectResource.getProjectId() + ";projectResourceId=" + projectResource.getId() + "，资源冻结状态更改为：" + freezed);
            projectResourceMapper.updateFreezedByFilename(freezed, filename);
        }
    }

    public void saveResourceScore(ProjectResourceScoreDto projectResourceScoreDto, int resourceId, int userId) throws InsertException {
        ProjectResourceScore projectResourceScore = new ProjectResourceScore();
        BeanUtils.copyProperties(projectResourceScoreDto, projectResourceScore);
        projectResourceScore.setResourceId(resourceId);
        projectResourceScore.setUserId(userId);
        try {
            projectResourceScoreMapper.insertSelective(projectResourceScore);
        } catch (
                DuplicateKeyException e) {
            throw new InsertException("您已评价过该资源！");
        } catch (
                DataIntegrityViolationException e) {
            throw new InsertException("资源不存在！");
        }
    }

    public PageVo<List<ProjectResourceScoreVo>> getAllResourceScorePagable(Integer pageNum, Integer pageSize, Integer projectId, Integer resourceId, BigDecimal lowestScore, BigDecimal highestScore) throws SelectException {
        if ((projectId == null && resourceId == null) || (projectId != null && resourceId != null)) {
            throw new SelectException("参数错误，projectId和resourceId有且只有一个字段为空！");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ProjectResourceScoreVo> projectResourceScoreVos = projectResourceScoreMapper.selectAllVoByConditions(projectId, resourceId, highestScore, lowestScore);
        PageInfo<ProjectResourceScoreVo> pageInfo = new PageInfo<>(projectResourceScoreVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    public boolean purchasedResource(int userId, int resourceId) {
        PurchasedResource purchasedResource = purchasedResourceMapper.selectByUserIdAndResourceId(userId, resourceId);
        return purchasedResource != null;
    }

    public List<PurchasedResourceVo> getAllPurchasedResourceVoByUserId(int userId) {
        return purchasedResourceMapper.selectAllVoByUserId(userId);
    }

    public void checkResourceByResourceId(ProjectResourceCheckDto projectResourceCheckDto, int resourceId) throws UpdateException {
        ProjectResource projectResource = projectResourceMapper.selectByPrimaryKey(resourceId);
        if (projectResource == null) {
            throw new UpdateException("项目资源不存在！");
        }
        projectResourceMapper.updatePriceAndDiscountById(projectResourceCheckDto.getPrice(), projectResourceCheckDto.getDiscount(), resourceId);
    }

    public boolean hasPermission(int userId, int projectResourceId) {
        ProjectResource projectResource = getProjectResourceById(projectResourceId);
        if (projectResource == null) {
            return false;
        }
        return projectService.hasPermission(userId, projectResource.getProjectId());
    }
}
