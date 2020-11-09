package cn.hdustea.aha_server;

import cn.hdustea.aha_server.entity.ProjectInfo;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.*;
import cn.hdustea.aha_server.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private OssService ossService;
    @Resource
    private UserService userService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectInfoService projectInfoService;
    @Resource
    private ProjectResourceService projectResourceService;
    @Resource
    private ContributionRankService contributionRankService;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetUser() throws SelectException {
        System.out.println(userService.getUserByPhone("15382355341"));
    }

    @Test
    void testAliOssUpload() {
        System.out.println(ossService.signUpload("abc/", false));
    }

    @Test
    void testOssBuildUrl() {
        System.out.println(ossService.buildPublicDownloadUrl("avatar/15677751219/1603972075808/avatar.JPG"));
    }

    @Test
    void testRedis() {
        redisUtil.incr("testaaaa", 1);
        System.out.println(redisUtil.get("testaaaa"));
    }

    @Test
    void testRank() {
        System.out.println(contributionRankService.getRankList());
    }

    @Test
    void testCRank() throws SelectException {
        System.out.println(contributionRankService.getUserContribPointByPhone("15382355341"));
    }

    @Test
    void testProject() {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setCompId(1);
        projectInfo.setAwardName("杭州电子科技大学服务外包创新创业大赛-校一等奖");
    }
}
