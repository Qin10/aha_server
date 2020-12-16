package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 贡献点排名服务类
 *
 * @author STEA_YY
 **/
@Service
public class ContributionRankService {
    @Resource
    private RedisService redisService;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取排行榜
     *
     * @return 排行榜列表
     */
    public List<UserContribPointVo> getRankList() {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisService.zGetTuple(RedisConstants.CONTRIBUTION_RANK_KEY, 0, 99);
        return redisService.tupleToUserContribPoint(tuples);
    }

    /**
     * 获取用户个人排名
     *
     * @param userInfoVo 用户信息封装
     * @return 用户排名
     * @throws SelectException 用户不存在异常
     */
    public long getRankByUserInfoVo(UserRoughInfoVo userInfoVo) throws SelectException {
        Long rank = redisService.zGetRank(RedisConstants.CONTRIBUTION_RANK_KEY, userInfoVo);
        if (rank == null) {
            throw new SelectException("未查询到排名!");
        }
        return rank + 1;
    }

    /**
     * 获取用户个人排名和贡献点
     *
     * @param userId 用户id
     * @return 用户个人排名和贡献点
     * @throws SelectException 用户不存在异常
     */
    public UserContribPointVo getUserContribPointByUserId(Integer userId) throws SelectException {
        UserRoughInfoVo userInfoVo = userInfoService.getUserInfoVoByUserId(userId);
        long rank = getRankByUserInfoVo(userInfoVo);
        Double contribPoint = redisService.zGetScore(RedisConstants.CONTRIBUTION_RANK_KEY, userInfoVo);
        UserContribPointVo userContribPointVo = new UserContribPointVo();
        userContribPointVo.setUser(userInfoVo);
        userContribPointVo.setContribPoint(BigDecimal.valueOf(contribPoint));
        userContribPointVo.setRank(rank);
        return userContribPointVo;
    }
}
