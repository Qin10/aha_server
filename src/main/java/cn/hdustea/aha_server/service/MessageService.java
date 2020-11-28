package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.MessageDto;
import cn.hdustea.aha_server.entity.Message;
import cn.hdustea.aha_server.entity.MessageText;
import cn.hdustea.aha_server.enums.MessageType;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.MessageMapper;
import cn.hdustea.aha_server.mapper.MessageTextMapper;
import cn.hdustea.aha_server.vo.MessageVo;
import cn.hdustea.aha_server.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 站内信服务类
 *
 * @author STEA_YY
 **/
@Service
public class MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private MessageTextMapper messageTextMapper;
    @Resource
    private UserService userService;

    public PageVo<List<MessageVo>> getAllMessageVoByReceiverPhonePagable(String receiverPhone, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MessageVo> messageVos = messageMapper.selectAllVoByReceiverPhone(receiverPhone);
        PageInfo<MessageVo> pageInfo = new PageInfo<>(messageVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    public MessageVo getMessageVoByIdAndReceiverPhone(int id, String reveiverPhone) {
        return messageMapper.selectVoByIdAndReceiverPhoneAndNotDeleted(id, reveiverPhone);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendPrivateMessage(MessageDto messageDto, String senderPhone) throws SelectException {
        userService.getExistUserByPhone(messageDto.getReveiverPhone());
        Message message = new Message();
        MessageText messageText = new MessageText();
        messageText.setSenderPhone(senderPhone);
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageType.TYPE_PRIVATE.getValue());
        messageTextMapper.insert(messageText);
        message.setTextId(messageText.getId());
        message.setReceiverPhone(messageDto.getReveiverPhone());
        message.setReceiveDate(new Date());
        messageMapper.insert(message);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendSystemMessage(MessageDto messageDto) {
        Message message = new Message();
        MessageText messageText = new MessageText();
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageType.TYPE_SYSTEM.getValue());
        messageTextMapper.insert(messageText);
        message.setTextId(messageText.getId());
        message.setReceiverPhone(messageDto.getReveiverPhone());
        message.setReceiveDate(new Date());
        messageMapper.insert(message);
    }

    public void sendSystemMessage(MessageDto messageDto, int textId) {
        Message message = new Message();
        message.setTextId(textId);
        message.setReceiverPhone(messageDto.getReveiverPhone());
        message.setReceiveDate(new Date());
        messageMapper.insert(message);
    }

    public void sendNoticeMessage(MessageDto messageDto, String senderPhone) {
        MessageText messageText = new MessageText();
        messageText.setSenderPhone(senderPhone);
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageType.TYPE_NOTICE.getValue());
        messageText.setPostDate(new Date());
        messageTextMapper.insert(messageText);
    }

    public void sendNoticeMessage(MessageDto messageDto) {
        sendNoticeMessage(messageDto, null);
    }

    public void updateNoticeTextById(MessageDto messageDto, int id) {
        MessageText messageText = new MessageText();
        messageText.setId(id);
        messageText.setContent(messageDto.getContent());
        messageTextMapper.updateByPrimaryKeySelective(messageText);
    }

    public void changeMessageStatusById(int status, int id) {
        messageMapper.updateStatusById(status, id);
    }

    public void changeMessageStatusByReceiverPhone(int status, String receiverPhone) {
        messageMapper.updateStatusByReceiverPhone(status, receiverPhone);
    }

    @Async
    public void saveAllNoticeNotReadByReceiverPhone(String receiverPhone) {
        List<MessageText> messageTexts = messageTextMapper.selectAllNoticeNotReadByReceivePhone(receiverPhone);
        List<Message> messages = new ArrayList<>();
        for (MessageText messageText : messageTexts) {
            Message message = new Message();
            message.setReceiverPhone(receiverPhone);
            message.setTextId(messageText.getId());
            message.setReceiveDate(messageText.getPostDate());
            message.setStatus(MessageType.STATUS_NOT_READ.getValue());
            messages.add(message);
        }
        if (!messages.isEmpty()) {
            messageMapper.insertList(messages);
        }
    }

}
