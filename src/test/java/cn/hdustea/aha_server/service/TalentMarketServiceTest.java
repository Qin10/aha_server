package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.vo.UserContribPointVo;
import cn.hdustea.aha_server.vo.UserRoughInfoVo;
import cn.hdustea.aha_server.vo.UserTalentMarketDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/4/10
 * @description: 人才市场业务测试类
 */
@Slf4j
@SpringBootTest
public class TalentMarketServiceTest {
    private final RedisService redisService;
    private final TalentMarketService marketService;

    @Autowired
    public TalentMarketServiceTest(RedisService redisService, TalentMarketService marketService) {
        this.redisService = redisService;
        this.marketService = marketService;
    }

    @Test
    void gogo() throws SelectException {
        log.info("GOGO");
        log.info(String.valueOf(redisService.get("gogo")));
        System.out.println(redisService.get("gogo"));
        List<UserContribPointVo> userContribPointVos = marketService.getAllOrderedTalentMarketRoughPageable(1, 10).getPageData();
        log.info(String.valueOf(userContribPointVos.size()));
        for (UserContribPointVo vo : userContribPointVos) {
            log.info(vo.toString());
        }

//        List<UserRoughInfoVo> userRoughInfoVos = marketService.getAllTalentMarketRoughPageable(1, 10).getPageData();
//        for(UserRoughInfoVo vo : userRoughInfoVos){
//            log.info(vo.toString());
//        }

//        UserTalentMarketDetailVo userTalentMarketDetailVo = marketService.getUserTalentMarketDetailById(100042);
//        log.info(userTalentMarketDetailVo.toString());
    }
}
