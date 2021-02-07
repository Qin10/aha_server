package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.constants.ContribPointLogConstants;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.dto.ActivityDto;
import cn.hdustea.aha_server.entity.Activity;
import cn.hdustea.aha_server.entity.ActivityCodeExchangeLog;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ActivityCodeExchangeLogMapper;
import cn.hdustea.aha_server.mapper.ActivityMapper;
import cn.hdustea.aha_server.util.CommonUtil;
import cn.hdustea.aha_server.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动服务类
 *
 * @author STEA_YY
 **/
@Service
public class ActivityService {
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private ContribPointService contribPointService;
    @Resource
    private ActivityCodeExchangeLogMapper activityCodeExchangeLogMapper;

    public PageVo<List<ActivityCodeExchangeLog>> getActivityCodeExchangeLogPagable(int pageNum, int pageSize, Integer userId, Integer activityId) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("acel_id desc");
        List<ActivityCodeExchangeLog> activityCodeExchangeLogs = activityCodeExchangeLogMapper.selectAllByConditions(userId, activityId);
        PageInfo<ActivityCodeExchangeLog> pageInfo = new PageInfo<>(activityCodeExchangeLogs);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
    }

    public PageVo<List<Activity>> getActivitiesPagable(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("act_id desc");
        List<Activity> activities = activityMapper.selectAll();
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
    }

    public Activity getExistedActivityById(int id) throws SelectException {
        Activity activity = activityMapper.selectByPrimaryKey(id);
        if (activity == null) {
            throw new SelectException("活动不存在！");
        }
        return activity;
    }

    public void saveActivityAndCreator(ActivityDto activityDto, int userId) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDto, activity);
        activity.setCreatorUserId(userId);
        activity.setCreateTime(new Date());
        activityMapper.insertSelective(activity);
        redisService.set(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX + activity.getId(), 0);
        redisService.expireAt(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX + activity.getId(), activity.getEndTime());
    }

    public void deleteActivityById(int id) {
        activityMapper.deleteByPrimaryKey(id);
        redisService.del(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX + id);
    }

    public List<String> generateCodeById(int id, int count) throws SelectException, PermissionDeniedException {
        Activity activity = getExistedActivityById(id);
        if (new Date().compareTo(activity.getEndTime()) > 0) {
            throw new PermissionDeniedException("活动已经截止！");
        }
        Integer sum = (Integer) redisService.get(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX + id);
        if (sum + count > activity.getCodeSum()) {
            throw new PermissionDeniedException("兑换码可生成额度不足！");
        }
        List<String> codes = new ArrayList<>();
        String tempCode;
        for (int i = 0; i < count; i++) {
            do {
                tempCode = CommonUtil.generateRandomString(8);
            } while (redisService.get(RedisConstants.ACTIVITY_CODE_PREFIX + tempCode) != null);
            redisService.set(RedisConstants.ACTIVITY_CODE_PREFIX + tempCode, id);
            redisService.expireAt(RedisConstants.ACTIVITY_CODE_PREFIX + tempCode, activity.getEndTime());
            Integer sumNow = (Integer) redisService.get(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX + id);
            if (sumNow + 1 > activity.getCodeSum()) {
                break;
            }
            redisService.incr(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX + id, 1);
            codes.add(tempCode);
        }
        return codes;
    }

    @Transactional(rollbackFor = Exception.class)
    public void exchangeCode(String code, int userId) throws PermissionDeniedException {
        Integer possibleActivityId = (Integer) redisService.get(RedisConstants.ACTIVITY_CODE_PREFIX + code);
        if (possibleActivityId == null) {
            throw new PermissionDeniedException("兑换码无效");
        }
        Activity activity = activityMapper.selectByPrimaryKey(possibleActivityId);
        if (activity == null) {
            throw new PermissionDeniedException("活动不存在！");
        }
        Date now = new Date();
        if (now.compareTo(activity.getStartTime()) < 0) {
            throw new PermissionDeniedException("活动还未开始！");
        }
        if (now.compareTo(activity.getEndTime()) > 0) {
            throw new PermissionDeniedException("活动已经结束！");
        }
        ActivityCodeExchangeLog activityCodeExchangeLog = new ActivityCodeExchangeLog();
        activityCodeExchangeLog.setUserId(userId);
        activityCodeExchangeLog.setActivityId(activity.getId());
        activityCodeExchangeLog.setCode(code);
        activityCodeExchangeLog.setExchangeTime(now);
        activityCodeExchangeLogMapper.insertSelective(activityCodeExchangeLog);
        contribPointService.sendContribPoint(userId, ContribPointLogConstants.FROM_ACTIVITY_EXCHANGE, activityCodeExchangeLog.getId(), activity.getExchangeAhaPoint(), activity.getExchangeAhaCredit());
        redisService.del(RedisConstants.ACTIVITY_CODE_PREFIX + code);
    }

    public Integer getCurrentCountById(int activityId) {
        return (Integer) redisService.get(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX + activityId);
    }
}
