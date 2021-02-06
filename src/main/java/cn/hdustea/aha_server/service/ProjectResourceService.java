package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.constants.ContribPointLogConstants;
import cn.hdustea.aha_server.constants.ProjectResourceCheckLogConstants;
import cn.hdustea.aha_server.constants.ProjectResourceConstants;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.dto.*;
import cn.hdustea.aha_server.entity.*;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.InsertException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.mapper.*;
import cn.hdustea.aha_server.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
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
    private ProjectResourceFinancialSchemeMapper projectResourceFinancialSchemeMapper;
    @Resource
    private ProjectResourceTypeMapper projectResourceTypeMapper;
    @Resource
    private ProjectResourceCheckLogMapper projectResourceCheckLogMapper;
    @Resource
    private CosService cosService;
    @Resource
    private ProjectService projectService;
    @Resource
    private RedisService redisService;
    @Resource
    private ContribPointService contribPointService;

    /**
     * 根据项目资源id获取项目资源
     *
     * @param id 项目资源id
     * @return 项目资源
     */
    public ProjectResource getProjectResourceById(int id) {
        return projectResourceMapper.selectByPrimaryKey(id);
    }

    public List<ProjectResourceVo> getAllProjectResourceVoByConditions(Boolean resourcePassed, Boolean projectPassed, Integer projectId) {
        return projectResourceMapper.selectAllVoByConditions(resourcePassed, projectPassed, projectId);
    }

    public PageVo<List<ProjectResourceVo>> getAllProjectResourceVoPagable(int pageNum, int pageSize, Boolean resourcePassed, Boolean projectPassed, Integer projectId) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("pr_id desc");
        List<ProjectResourceVo> projectResourceVos = projectResourceMapper.selectAllVoByConditions(resourcePassed, projectPassed, projectId);
        PageInfo<ProjectResourceVo> pageInfo = new PageInfo<>(projectResourceVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
    }

    /**
     * 根据项目资源id获取项目资源VO
     *
     * @param id 项目资源id
     * @return 项目资源
     */
    public ProjectResourceVo getProjectResourceVoByIdAndPassed(int id, Boolean passed) {
        return projectResourceMapper.selectVoByPrimaryKeyAndPassed(id, passed);
    }

    /**
     * 根据项目资源id获取COS下载签名
     *
     * @param id 项目资源id
     * @return 下载url
     * @throws SelectException 查询异常
     */
    public CosPolicyVo signDownloadProjectResourceByIdToCos(int id) throws SelectException {
        ProjectResource projectResource = getProjectResourceById(id);
        if (projectResource == null) {
            throw new SelectException("不存在对应记录！");
        }
        if (projectResource.getFilename() == null) {
            throw new SelectException("资源文件为空！");
        }
        return cosService.signDownloadAuthorization(projectResource.getFilename());
    }

    /**
     * 根据项目资源id获取COS在线阅读签名
     *
     * @param id 项目资源id
     * @return 下载url
     * @throws SelectException 查询异常
     */
    public CosPolicyVo signPreviewProjectResourceByIdToCos(int id) throws SelectException {
        ProjectResource projectResource = getProjectResourceById(id);
        if (projectResource == null) {
            throw new SelectException("不存在对应记录！");
        }
        if (projectResource.getFilename() == null) {
            throw new SelectException("资源文件为空！");
        }
        if (projectResource.getFileType() == null || projectResource.getFileType() != ProjectResourceConstants.TYPE_DOCUMENT) {
            throw new SelectException("资源类型错误！");
        }
        return cosService.signPreviewAuthorization(projectResource.getFilename());
    }

    /**
     * 新增项目资源
     *
     * @param projectResourceDto 项目资源
     * @param projectId          项目id
     */
    public Integer saveProjectResourceByProjectId(ProjectResourceDto projectResourceDto, int projectId) throws SelectException, InsertException {
        Project project = projectService.getProjectById(projectId);
        if (!checkResourcePrice(project.getAwardLevel(), projectResourceDto.getTypeId(), projectResourceDto.getPrice())) {
            throw new InsertException("资源定价不符合规范！");
        }
        ProjectResource projectResource = new ProjectResource();
        BeanUtils.copyProperties(projectResourceDto, projectResource);
        projectResource.setProjectId(projectId);
        projectResourceMapper.insertSelective(projectResource);
        return projectResource.getId();
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
     * 保存项目资源评价
     *
     * @param projectResourceScoreDto 项目资源评价
     * @param resourceId              项目资源id
     * @param userId                  用户id
     * @throws InsertException 插入异常
     */
    public void saveResourceScore(ProjectResourceScoreDto projectResourceScoreDto, int resourceId, int userId) throws InsertException {
        ProjectResourceScore projectResourceScore = new ProjectResourceScore();
        BeanUtils.copyProperties(projectResourceScoreDto, projectResourceScore);
        projectResourceScore.setResourceId(resourceId);
        projectResourceScore.setUserId(userId);
        projectResourceScore.setTime(new Date());
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

    /**
     * 删除项目资源评价
     *
     * @param resourceId 项目资源id
     * @param userId     用户id
     * @throws DeleteException 删除异常
     */
    public void deleteResourceScore(int resourceId, int userId) throws DeleteException {
        ProjectResourceScore projectResourceScore = projectResourceScoreMapper.selectByPrimaryKey(userId, resourceId);
        if (projectResourceScore == null) {
            throw new DeleteException("评论不存在！");
        }
        projectResourceScoreMapper.deleteByPrimaryKey(userId, resourceId);
    }

    /**
     * 分页获取项目资源评价
     *
     * @param pageNum      页码
     * @param pageSize     分页大小
     * @param projectId    项目id
     * @param resourceId   项目资源id
     * @param lowestScore  最低分
     * @param highestScore 最高分
     * @param sortBy       排序关键字,取值time、score
     * @param orderBy      排序方式
     * @return 项目资源分页列表
     * @throws SelectException 查询异常
     */
    public PageVo<List<ProjectResourceScoreVo>> getAllResourceScorePagable(Integer pageNum, Integer pageSize, Integer projectId, Integer resourceId, BigDecimal lowestScore, BigDecimal highestScore, String sortBy, String orderBy) throws SelectException {
        if ((projectId == null && resourceId == null) || (projectId != null && resourceId != null)) {
            throw new SelectException("参数错误，projectId和resourceId有且只有一个字段为空！");
        }
        String currentSortBy;
        String currentOrderBy;
        switch (sortBy) {
            case "time": {
                currentSortBy = "prs_time";
                break;
            }
            case "score": {
                currentSortBy = "prs_score";
                break;
            }
            default: {
                throw new SelectException("'sortBy'参数取值错误！");
            }
        }
        switch (orderBy) {
            case "desc": {
                currentOrderBy = "desc";
                break;
            }
            case "asc": {
                currentOrderBy = "asc";
                break;
            }
            default: {
                throw new SelectException("'orderBy'参数取值错误！");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy(currentSortBy + " " + currentOrderBy);
        List<ProjectResourceScoreVo> projectResourceScoreVos = projectResourceScoreMapper.selectAllVoByConditions(projectId, resourceId, highestScore, lowestScore, true);
        PageInfo<ProjectResourceScoreVo> pageInfo = new PageInfo<>(projectResourceScoreVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    /**
     * 判断用户是否购买了资源
     *
     * @param userId     用户id
     * @param resourceId 项目资源id
     * @return 是否购买
     */
    public boolean purchasedResource(int userId, int resourceId) {
        PurchasedResource purchasedResource = purchasedResourceMapper.selectByPrimaryKey(userId, resourceId);
        return purchasedResource != null;
    }

    public boolean allowDownload(int userId, int resourceId) {
        if (hasPermission(userId, resourceId)) {
            return true;
        }
        if (purchasedResource(userId, resourceId)) {
            ProjectResource projectResource = getProjectResourceById(resourceId);
            return projectResource.getFileType() != null && projectResource.getFileType() != ProjectResourceConstants.TYPE_DOCUMENT;
        }
        return false;
    }

    /**
     * 获取用户全部已购资源
     *
     * @param userId 用户id
     * @return 已购资源列表
     */
    public List<PurchasedResourceVo> getAllPurchasedResourceVoByUserId(int userId) {
        return purchasedResourceMapper.selectAllVoByUserId(userId);
    }

    /**
     * 分页获取资源被购买记录
     *
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @param resourceId 项目资源id
     * @return 分页资源购买信息列表
     */
    public PageVo<List<PurchasedResourceManagementVo>> getAllPurchasedResourceVoByResourceId(int pageNum, int pageSize, Integer resourceId, Integer projectId, Integer userId, Integer orderId) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("pur_purchase_time desc");
        List<PurchasedResourceManagementVo> purchasedResourceVos = purchasedResourceMapper.selectAllManagementVoByConditions(resourceId, projectId, userId, orderId);
        PageInfo<PurchasedResourceManagementVo> pageInfo = new PageInfo<>(purchasedResourceVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void checkResourceByProjectId(boolean passed, Integer projectId) throws UpdateException, SelectException {
        Project project = projectService.getProjectById(projectId);
        if (passed) {
            List<ProjectResource> projectResources = projectResourceMapper.selectAllByProjectId(projectId);
            for (ProjectResource projectResource : projectResources) {
                if (!projectResource.getPassed()) {
                    sendReward(project, projectResource);
                    addToConvertList(projectResource);
                }
            }
        }
        projectResourceMapper.updatePassedByProjectId(passed, projectId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendReward(Project project, ProjectResource projectResource) throws SelectException, UpdateException {
        boolean checkResult = checkResourcePrice(project.getAwardLevel(), projectResource.getTypeId(), projectResource.getPrice());
        if (!checkResult) {
            throw new UpdateException("资源id=" + projectResource.getId() + "定价不合规范！");
        }
        BigDecimal rewardAhaCredit = ProjectResourceConstants.REWARD_COEFFICIENT.multiply(projectResource.getPrice());
        contribPointService.sendContribPoint(project.getCreatorUserId(), ContribPointLogConstants.FROM_CONTRIBUTE_RESOURCES, projectResource.getId(), null, rewardAhaCredit);
    }

    /**
     * 审核项目资源
     *
     * @param projectResourceCheckDto 项目资源审核信息
     * @param resourceId              项目资源id
     * @throws UpdateException 更新异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void checkResourceByResourceId(int operateUserId, ProjectResourceCheckDto projectResourceCheckDto, int resourceId) throws UpdateException, SelectException {
        ProjectResource projectResource = projectResourceMapper.selectByPrimaryKey(resourceId);
        if (projectResource == null) {
            throw new SelectException("项目资源不存在！");
        }
        ProjectResourceCheckLog projectResourceCheckLog = new ProjectResourceCheckLog();
        projectResourceCheckLog.setResourceId(resourceId);
        projectResourceCheckLog.setOperateUserId(operateUserId);
        projectResourceCheckLog.setOperateTime(new Date());
        if (projectResourceCheckDto.getPassed()) {
            projectResourceCheckLog.setCheckStatus(ProjectResourceCheckLogConstants.STATUS_PASS);
            if (!projectResource.getPassed()) {
                Project project = projectService.getProjectById(projectResource.getProjectId());
                List<ProjectResourceCheckLog> passedLogs = projectResourceCheckLogMapper.selectByResourceIdAndCheckStatus(resourceId, ProjectResourceCheckLogConstants.STATUS_PASS);
                if (passedLogs == null || passedLogs.isEmpty()) {
                    sendReward(project, projectResource);
                }
                addToConvertList(projectResource);
            }
        } else {
            projectResourceCheckLog.setCheckStatus(ProjectResourceCheckLogConstants.STATUS_NOT_PASS);
        }
        projectResource.setPassed(projectResourceCheckDto.getPassed());
        projectResourceMapper.updateByPrimaryKeySelective(projectResource);
        projectResourceCheckLogMapper.insertSelective(projectResourceCheckLog);
    }

    /**
     * 判断用户是否有权限编辑项目资源
     *
     * @param userId            用户id
     * @param projectResourceId 项目资源id
     * @return 是否有权限
     */
    public boolean hasPermission(int userId, int projectResourceId) {
        ProjectResource projectResource = getProjectResourceById(projectResourceId);
        if (projectResource == null) {
            return false;
        }
        return projectService.hasPermission(userId, projectResource.getProjectId());
    }

    public Integer[] getAllPurchasedResourceIdsByProjectIdAndUserId(int projectId, int userId) {
        return purchasedResourceMapper.selectAllResourceIdByProjectIdAndUserId(projectId, userId).toArray(new Integer[0]);
    }

    public boolean isPassed(int resourceId) {
        ProjectResource projectResource = projectResourceMapper.selectByPrimaryKey(resourceId);
        if (projectResource == null) {
            return false;
        } else {
            return projectResource.getPassed();
        }
    }

    private void addToConvertList(ProjectResource projectResource) {
        if (projectResource.getFileType() == ProjectResourceConstants.TYPE_DOCUMENT && projectResource.getPreviewUrl() == null) {
            DocumentConvertInfoDto documentConvertInfoDto = new DocumentConvertInfoDto();
            documentConvertInfoDto.setProjectResourceId(projectResource.getId());
            documentConvertInfoDto.setSrcFilename(projectResource.getFilename());
            redisService.lPush(RedisConstants.DOCUMENT_CONVERT_LIST_KEY, documentConvertInfoDto);
        }
    }

    public boolean checkResourcePrice(int awardLevel, int typeId, BigDecimal price) throws SelectException {
        ProjectResourceFinancialScheme projectResourceFinancialScheme = projectResourceFinancialSchemeMapper.selectByAwardLevel(awardLevel);
        if (projectResourceFinancialScheme == null) {
            throw new SelectException("项目获奖等级取值错误！");
        }
        ProjectResourceType projectResourceType = projectResourceTypeMapper.selectByPrimaryKey(typeId);
        BigDecimal upperLimit = projectResourceType.getPriceCoefficient().multiply(projectResourceFinancialScheme.getPriceUpperLimit());
        BigDecimal lowerLimit = projectResourceType.getPriceCoefficient().multiply(projectResourceFinancialScheme.getPriceLowerLimit());
        return price.compareTo(upperLimit) <= 0 && price.compareTo(lowerLimit) >= 0;
    }

    public List<ProjectResourceFinancialScheme> getAllProjectResourceFinancialScheme() {
        return projectResourceFinancialSchemeMapper.selectAll();
    }

    public List<ProjectResourceType> getAllProjectResourceType() {
        return projectResourceTypeMapper.selectAll();
    }
}
