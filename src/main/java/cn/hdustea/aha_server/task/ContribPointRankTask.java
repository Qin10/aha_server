package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.mapper.UserMapper;
import cn.hdustea.aha_server.util.RedisUtil;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 贡献点排行榜的任务类
 *
 * @author STEA_YY
 **/
@Component
@Slf4j
public class ContribPointRankTask {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 更新排行榜
     */
    @Scheduled(cron = "0 0 * * * ?")
    @Async
    public void getRank() {
        log.debug("Contrib Rank Task is Running");
        List<UserContribPointVo> userContribPointVos = userMapper.selectIdAndContribPoint();
        redisUtil.del(RedisConstants.CONTRIBUTION_RANK_KEY);
        for (UserContribPointVo userContribPointVo : userContribPointVos) {
            redisUtil.zSet(RedisConstants.CONTRIBUTION_RANK_KEY, userContribPointVo.getUser(), userContribPointVo.getContribPoint().doubleValue());
        }
    }
}
