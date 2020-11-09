package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.dto.UserContribPointBean;
import cn.hdustea.aha_server.mapper.UserMapper;
import cn.hdustea.aha_server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Scheduled(cron = "0 0 * * * ?")
    public void getRank() {
        log.debug("Contrib Rank Task is Running");
        List<UserContribPointBean> userContribPointBeans = userMapper.selectPhoneAndContribPoint();
        redisUtil.del(RedisUtil.CONTRIBUTION_RANK_KEY);
        for (UserContribPointBean userContribPointBean : userContribPointBeans) {
            redisUtil.zSSet(RedisUtil.CONTRIBUTION_RANK_KEY, userContribPointBean.getPhone(), userContribPointBean.getContribPoint().doubleValue());
        }
    }
}
