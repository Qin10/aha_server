package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.util.RedisUtil;
import cn.hdustea.aha_server.vo.UserContribPointVo;
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
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;

    /**
     * 获取排行榜
     *
     * @return 排行榜列表
     */
    public List<UserContribPointVo> getRankList() {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisUtil.zSGetTuple(RedisUtil.CONTRIBUTION_RANK_KEY, 0, 99);
        return redisUtil.tupleToUserContribPoint(tuples);
    }

    /**
     * 获取用户个人排名
     *
     * @param userId 用户id
     * @return 用户排名
     * @throws SelectException 用户不存在异常
     */
    public long getRankByUserId(Integer userId) throws SelectException {
        Long rank = redisUtil.zSGetRank(RedisUtil.CONTRIBUTION_RANK_KEY, userId);
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
        long rank = getRankByUserId(userId);
        Double contribPoint = redisUtil.zSGetScore(RedisUtil.CONTRIBUTION_RANK_KEY, userId);
        UserContribPointVo userContribPointVo = new UserContribPointVo();
        userContribPointVo.setUserId(userId);
        userContribPointVo.setContribPoint(BigDecimal.valueOf(contribPoint));
        userContribPointVo.setRank(rank);
        return userContribPointVo;
    }
}
