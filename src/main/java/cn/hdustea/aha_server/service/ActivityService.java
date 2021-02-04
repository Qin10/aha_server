package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.constants.ContribPointLogConstants;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.dto.ActivityDto;
import cn.hdustea.aha_server.entity.Activity;
import cn.hdustea.aha_server.entity.ActivityCodeExchangeLog;
import cn.hdustea.aha_server.entity.ContribPointLog;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.ActivityCodeExchangeLogMapper;
import cn.hdustea.aha_server.mapper.ActivityMapper;
import cn.hdustea.aha_server.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private UserService userService;
    @Resource
    private ContribPointLogService contribPointLogService;
    @Resource
    private ActivityCodeExchangeLogMapper activityCodeExchangeLogMapper;

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
        Integer sum = (Integer) redisService.get(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX);
        if (sum + count > activity.getCodeSum()) {
            throw new PermissionDeniedException("兑换码可生成额度不足！");
        }
        List<String> codes = new ArrayList<>();
        String tempCode;
        for (int i = 0; i < count; i++) {
            do {
                tempCode = CommonUtil.generateRandomString(8);
            } while (redisService.get(RedisConstants.ACTIVITY_CODE_PREFIX + tempCode) == null);
            redisService.set(RedisConstants.ACTIVITY_CODE_PREFIX + tempCode, id);
            redisService.expireAt(RedisConstants.ACTIVITY_CODE_PREFIX + tempCode, activity.getEndTime());
            Integer sumNow = (Integer) redisService.get(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX);
            if (sumNow + 1 > activity.getCodeSum()) {
                break;
            }
            redisService.incr(RedisConstants.ACTIVITY_CODE_COUNT_PREFIX, 1);
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
        ContribPointLog contribPointLog = new ContribPointLog();
        contribPointLog.setUserId(userId);
        contribPointLog.setType(ContribPointLogConstants.FROM_ACTIVITY_EXCHANGE);
        contribPointLog.setExternalId(activityCodeExchangeLog.getId());
        contribPointLog.setTime(now);
        if (activity.getExchangeAhaPoint().compareTo(BigDecimal.ZERO) > 0) {
            userService.updateIncAhaPoint(userId, activity.getExchangeAhaPoint());
            contribPointLog.setAhaPointAmount(activity.getExchangeAhaPoint());
        }
        if (activity.getExchangeAhaCredit().compareTo(BigDecimal.ZERO) > 0) {
            userService.updateIncAhaCredit(userId, activity.getExchangeAhaCredit());
            contribPointLog.setAhaCreditAmount(activity.getExchangeAhaCredit());
        }
        contribPointLogService.saveContribPointLog(contribPointLog);
        redisService.del(RedisConstants.ACTIVITY_CODE_PREFIX + code);
    }
}
