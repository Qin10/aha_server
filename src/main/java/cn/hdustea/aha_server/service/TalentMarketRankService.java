package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/4/11
 * @description: 人才市场排名服务类
 */
@Service
public class TalentMarketRankService {
    @Resource
    private RedisService redisService;

    /**
     * @return 人才市场推荐榜列表（100条）
     */
    public List<UserContribPointVo> getTalentMarketRankList() {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisService.zGetTuple(RedisConstants.TALENTMARKET_CONTRIBUTION_RANK_KEY, 0, 199);
        return redisService.tupleToUserContribPoint(tuples);
    }

    /**
     * @param start
     * @param end
     * @return  人才市场推荐榜列表（自定义推荐数）
     */
    public List<UserContribPointVo> getTalentMarketRankList(int start, int end) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisService.zGetTuple(RedisConstants.TALENTMARKET_CONTRIBUTION_RANK_KEY, start, end);
        return redisService.tupleToUserContribPoint(tuples);
    }
}
