package cn.hdustea.aha_server;

import cn.hdustea.aha_server.dto.MessageDto;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.MessageService;
import cn.hdustea.aha_server.service.ProjectResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private MessageService messageService;
    @Resource
    private ProjectResourceService projectResourceService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsertMessage() throws SelectException {
        MessageDto messageDto = new MessageDto();
        messageDto.setTitle("Test Message");
        messageDto.setContent("This is a test message");
        messageDto.setReceiverUserId(54);
        messageService.sendPrivateMessage(messageDto, 53);
    }

    @Test
    public void testScore() throws SelectException {
        System.out.println(projectResourceService.getAllResourceScorePagable(1, 20, 72, null, BigDecimal.valueOf(0.0), BigDecimal.valueOf(5.0), "time", "desc"));
    }
}
