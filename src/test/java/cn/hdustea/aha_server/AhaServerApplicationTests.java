package cn.hdustea.aha_server;

import cn.hdustea.aha_server.dto.MessageDto;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AhaServerApplicationTests {
    @Resource
    private MessageService messageService;

    @Test
    void contextLoads() {
    }

    public void testInsertMessage() throws SelectException {
        MessageDto messageDto = new MessageDto();
        messageDto.setTitle("Test Message");
        messageDto.setContent("This is a test message");
        messageDto.setReceiverUserId(54);
        messageService.sendPrivateMessage(messageDto,53);
    }
}
