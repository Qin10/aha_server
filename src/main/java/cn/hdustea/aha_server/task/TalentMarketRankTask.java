package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.mapper.UserInfoMapper;
import cn.hdustea.aha_server.service.RedisService;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/4/11
 * @description: 人才市场推荐榜的定时任务
 */
@Component
@Slf4j
public class TalentMarketRankTask {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RedisService redisService;

    /**
     * 更新人才市场推荐表
     */
    @Scheduled(cron = "0 0 * * * ?")
    @Async
    public void getTalentMarketRank() {
        log.debug("Talent Market Rank Task is Running");
        List<UserContribPointVo> userContribPointVos = userInfoMapper.selectAllTalentMarketRoughVoOrderByContrib();
        redisService.del(RedisConstants.TALENTMARKET_CONTRIBUTION_RANK_KEY);
        for(UserContribPointVo userContribPointVo : userContribPointVos){
            redisService.zSet(RedisConstants.TALENTMARKET_CONTRIBUTION_RANK_KEY, userContribPointVo.getUser(), userContribPointVo.getContribPoint().doubleValue());
        }
    }
}
