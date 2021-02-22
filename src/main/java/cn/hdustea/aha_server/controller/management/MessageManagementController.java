package cn.hdustea.aha_server.controller.management;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.MessageDto;
import cn.hdustea.aha_server.service.MessageService;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 站内信后台管理类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin/message")
public class MessageManagementController {
    @Resource
    private MessageService messageService;

    /**
     * 发送群发系统消息(广播)
     *
     * @param messageDto 站内信
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/systemNotice")
    public ResponseBean<Object> sendSystemNotice(@RequestBody MessageDto messageDto) {
        messageService.sendSystemNoticeMessage(messageDto);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 向指定用户发送系统消息
     *
     * @param messageDto 站内信
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/systemPrivate")
    public ResponseBean<Object> sendSystemMessage(@RequestBody MessageDto messageDto) {
        messageService.sendSystemMessage(messageDto);
        return new ResponseBean<>(200, "succ", null);
    }
}
