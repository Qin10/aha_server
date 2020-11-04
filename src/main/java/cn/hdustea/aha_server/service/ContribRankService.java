package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.User;
import cn.hdustea.aha_server.entity.UserContribPoint;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.util.RedisUtil;
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
public class ContribRankService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;

    public List<UserContribPoint> getRankList() {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisUtil.zSGetTuple(RedisUtil.CONTRIB_RANK_KEY, 0, 99);
        return redisUtil.tupleToUserContribPoint(tuples);
    }

    public long getRankByPhone(String phone) throws SelectException {
        Long rank = redisUtil.zSGetRank(RedisUtil.CONTRIB_RANK_KEY, phone);
        if (rank == null) {
            throw new SelectException("未查询到排名!");
        }
        return rank + 1;
    }

    public UserContribPoint getUserContribPointByPhone(String phone) throws SelectException {
        long rank = getRankByPhone(phone);
        Double contribPoint = redisUtil.zSGetScore(RedisUtil.CONTRIB_RANK_KEY, phone);
        User user = userService.getUserByPhone(phone);
        UserContribPoint userContribPoint = new UserContribPoint();
        userContribPoint.setPhone(user.getPhone());
        userContribPoint.setContribPoint(BigDecimal.valueOf(contribPoint));
        userContribPoint.setRank(rank);
        return userContribPoint;
    }
}
