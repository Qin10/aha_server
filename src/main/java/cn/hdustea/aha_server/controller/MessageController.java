package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.MessageDto;
import cn.hdustea.aha_server.enums.MessageType;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.MessageService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.MessageVo;
import cn.hdustea.aha_server.vo.PageVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 站内信相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @RequiresLogin
    @GetMapping("/count/notRead")
    public ResponseBean<Integer> getMessageCountNotRead(@RequestParam(value = "type", required = false) Integer type) {
        String phone = ThreadLocalUtil.getCurrentUser();
        int count = messageService.getMessageCountNotReadByReceiverPhoneAndType(phone, type);
        return new ResponseBean<>(200, "succ", count);
    }

    @RequiresLogin
    @GetMapping()
    public ResponseBean<PageVo<List<MessageVo>>> getAllMessageVoPagable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "type", required = false) Integer type) {
        String phone = ThreadLocalUtil.getCurrentUser();
        PageVo<List<MessageVo>> messageVos = messageService.getAllMessageVoByReceiverPhonePagable(phone, pageNum, pageSize, status, type);
        return new ResponseBean<>(200, "succ", messageVos);
    }

    @RequiresLogin
    @GetMapping("/{messageId}")
    public ResponseBean<Object> getMessageVoById(@PathVariable("messageId") int messageId) {
        String phone = ThreadLocalUtil.getCurrentUser();
        MessageVo messageVo = messageService.getMessageVoByIdAndReceiverPhone(messageId, phone);
        if (messageVo != null) {
            if (messageVo.getStatus() == MessageType.STATUS_NOT_READ.getValue()) {
                messageService.changeMessageStatusById(MessageType.STATUS_READ.getValue(), messageId);
            }
        }
        return new ResponseBean<>(200, "succ", messageVo);
    }

    @RequiresLogin
    @DeleteMapping("/{messageId}")
    public ResponseBean<Object> deleteMessageVoById(@PathVariable("messageId") int messageId) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (messageService.isReceiver(phone, messageId)) {
            messageService.changeMessageStatusById(MessageType.STATUS_DELETED.getValue(), messageId);
        } else {
            throw new PermissionDeniedException();
        }
        return new ResponseBean<>(200, "succ", null);
    }

    @RequiresLogin
    @PostMapping()
    public ResponseBean<Object> sendPrivateMessage(@Validated @RequestBody MessageDto messageDto) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        messageService.sendPrivateMessage(messageDto, phone);
        return new ResponseBean<>(200, "succ", null);
    }
}