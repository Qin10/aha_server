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

    /**
     * 根据条件获取未读消息条数
     *
     * @param type 消息类型(system:系统消息,private:私信)
     */
    @RequiresLogin
    @GetMapping("/count/notRead")
    public ResponseBean<Integer> getMessageCountNotRead(@RequestParam(value = "type", required = false) String type) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        int count = messageService.getMessageCountNotReadByReceiverPhoneAndType(phone, type);
        return new ResponseBean<>(200, "succ", count);
    }

    /**
     * 根据条件获取消息队列
     *
     * @param pageNum  页码
     * @param pageSize 分页大小
     * @param status   状态(0:未读,1:已读,2:已删除)
     * @param type     消息类型(system:系统消息,private:私信)
     */
    @RequiresLogin
    @GetMapping()
    public ResponseBean<PageVo<List<MessageVo>>> getAllMessageVoPagable(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize, @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "type", required = false) String type) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        PageVo<List<MessageVo>> messageVos = messageService.getAllMessageVoByReceiverPhonePagable(phone, pageNum, pageSize, status, type);
        return new ResponseBean<>(200, "succ", messageVos);
    }

    /**
     * 根据对方手机号获取会话消息，并标记为已读
     *
     * @param phone 对方手机号
     */
    @RequiresLogin
    @GetMapping("/communication/{phone}")
    public ResponseBean<List<MessageVo>> getMessageInCommunication(@PathVariable("phone") String phone) {
        String userPhone = ThreadLocalUtil.getCurrentUser();
        List<MessageVo> messageVos = messageService.getAllMessageVoInCommunicationBySenderPhoneAndReceiverPhone(userPhone, phone);
        if (!messageVos.isEmpty()) {
            messageService.changeMessageStatusByReceiverPhoneAndSenderPhone(MessageType.STATUS_READ.getValue(), userPhone, phone);
        }
        return new ResponseBean<>(200, "succ", messageVos);
    }

    /**
     * 根据id获取站内信内容，并标记为已读
     *
     * @param messageId 站内信id
     */
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

    /**
     * 删除指定站内信（并非真的从数据库中删除）
     *
     * @param messageId 站内信id
     */
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

    /**
     * 向指定用户发送私信
     *
     * @param messageDto 站内信
     */
    @RequiresLogin
    @PostMapping()
    public ResponseBean<Object> sendPrivateMessage(@Validated @RequestBody MessageDto messageDto) throws SelectException {
        String phone = ThreadLocalUtil.getCurrentUser();
        messageService.sendPrivateMessage(messageDto, phone);
        return new ResponseBean<>(200, "succ", null);
    }
}