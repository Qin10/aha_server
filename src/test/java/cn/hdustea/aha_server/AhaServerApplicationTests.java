package cn.hdustea.aha_server;

import cn.hdustea.aha_server.config.TencentCosConfig;
import cn.hdustea.aha_server.service.CosService;
import cn.hdustea.aha_server.service.MessageService;
import cn.hdustea.aha_server.service.ProjectResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private MessageService messageService;
    @Resource
    private ProjectResourceService projectResourceService;
    @Resource
    private CosService cosService;
    @Resource
    private TencentCosConfig tencentCosConfig;

    @Test
    void contextLoads() {
    }
}
