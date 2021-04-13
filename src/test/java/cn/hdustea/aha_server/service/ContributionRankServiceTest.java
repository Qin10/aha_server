package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/4/10
 * @description: 贡献点排名服务测试类
 */
@Slf4j
@SpringBootTest
public class ContributionRankServiceTest {
    private final RedisService redisService;
    private final ContributionRankService contributionRankService;

    @Autowired
    public ContributionRankServiceTest(RedisService redisService, ContributionRankService contributionRankService) {
        this.redisService = redisService;
        this.contributionRankService = contributionRankService;
    }

    @Test
    void gogo() throws SelectException {
        log.info("GOGO");
        log.info(String.valueOf(redisService.get("gogo")));
        log.info("======================");
        List<UserContribPointVo> rankList = contributionRankService.getRankList();
        log.info(String.valueOf(rankList.size()));
        for (UserContribPointVo list : rankList) {
            log.info(list.toString());
        }
    }
}
