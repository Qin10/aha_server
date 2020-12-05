package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.constants.MessageConstants;
import cn.hdustea.aha_server.dto.MessageDto;
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
        Integer userId = ThreadLocalUtil.getCurrentUser();
        int count = messageService.getMessageCountNotReadByReceiverUserIdAndType(userId, type);
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
        Integer userId = ThreadLocalUtil.getCurrentUser();
        PageVo<List<MessageVo>> messageVos = messageService.getAllMessageVoByReceiverUserIdPagable(userId, pageNum, pageSize, status, type);
        return new ResponseBean<>(200, "succ", messageVos);
    }

    /**
     * 根据对方用户id获取会话消息，并标记为已读
     *
     * @param senderUserId 对方用户id
     */
    @RequiresLogin
    @GetMapping("/communication/{senderUserId}")
    public ResponseBean<List<MessageVo>> getMessageInCommunication(@PathVariable("senderUserId") Integer senderUserId) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        List<MessageVo> messageVos = messageService.getAllMessageVoInCommunicationBySenderUserIdAndReceiverUserId(userId, senderUserId);
        if (!messageVos.isEmpty()) {
            messageService.changeMessageStatusByReceiverUserIdAndSenderUserId(MessageConstants.STATUS_READ, userId, senderUserId);
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
        Integer userId = ThreadLocalUtil.getCurrentUser();
        MessageVo messageVo = messageService.getMessageVoByIdAndReceiverUserId(messageId, userId);
        if (messageVo != null) {
            if (messageVo.getStatus() == MessageConstants.STATUS_NOT_READ) {
                messageService.changeMessageStatusById(MessageConstants.STATUS_READ, messageId);
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
        Integer userId = ThreadLocalUtil.getCurrentUser();
        if (messageService.isReceiver(userId, messageId)) {
            messageService.changeMessageStatusById(MessageConstants.STATUS_DELETED, messageId);
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
        Integer userId = ThreadLocalUtil.getCurrentUser();
        messageService.sendPrivateMessage(messageDto, userId);
        return new ResponseBean<>(200, "succ", null);
    }
}