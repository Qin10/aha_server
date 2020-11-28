package cn.hdustea.aha_server;

import cn.hdustea.aha_server.dto.DocumentConvertInfoDto;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.*;
import cn.hdustea.aha_server.task.OssDocumentConvertTask;
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
    private ProjectResourceService projectResourceService;
    @Resource
    private ContributionRankService contributionRankService;
    @Resource
    private OssDocumentConvertTask ossDocumentConvertTask;
    @Resource
    private MessageService messageService;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetUser() throws SelectException {
        System.out.println(userService.getExistUserByPhone("15382355341"));
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
    void testMNS() {
        DocumentConvertInfoDto documentConvertInfoDto = new DocumentConvertInfoDto();
        documentConvertInfoDto.setProjectResourceId(8);
        documentConvertInfoDto.setSrcFilename("企业画像/1606217593868/DJI_20180819_082700.mp4");
        redisUtil.lPush(RedisUtil.DOCUMENT_CONVERT_LIST_KEY,documentConvertInfoDto);
    }

    @Test
    void testMessageService(){
        messageService.saveAllNoticeNotReadByReceiverPhone("15382355341");
    }
}
