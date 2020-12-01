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

    public PageVo<List<MessageVo>> getAllMessageVoByReceiverUserIdPagable(int receiverUserId, int pageNum, int pageSize, Integer status, String type) throws SelectException {
        Integer currentType = null;
        if (status != null && (status < 0 || status > 2)) {
            throw new SelectException("'status'参数取值错误");
        }
        if (type != null) {
            switch (type) {
                case "system": {
                    currentType = MessageType.TYPE_SYSTEM.getValue();
                    break;
                }
                case "private": {
                    currentType = MessageType.TYPE_PRIVATE.getValue();
                    break;
                }
                default: {
                    throw new SelectException("'type'参数取值错误");
                }
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<MessageVo> messageVos = messageMapper.selectAllVoByConditions(receiverUserId, status, currentType);
        PageInfo<MessageVo> pageInfo = new PageInfo<>(messageVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    public MessageVo getMessageVoByIdAndReceiverUserId(int id, int receiverUserId) {
        return messageMapper.selectVoByIdAndReceiverUserIdAndNotDeleted(id, receiverUserId);
    }

    public List<MessageVo> getAllMessageVoInCommunicationBySenderUserIdAndReceiverUserId(int receiverUserId, int senderUserId) {
        return messageMapper.selectAllVoInCommunicationBySenderUserIdAndReceiverUserId(receiverUserId, senderUserId);
    }

    public int getMessageCountNotReadByReceiverUserIdAndType(int receiverUserId, String type) throws SelectException {
        Integer currentType = null;
        if (type != null) {
            switch (type) {
                case "system": {
                    currentType = MessageType.TYPE_SYSTEM.getValue();
                    break;
                }
                case "private": {
                    currentType = MessageType.TYPE_PRIVATE.getValue();
                    break;
                }
                default: {
                    throw new SelectException("'type'参数取值错误");
                }
            }
        }
        return messageMapper.countByReceiverUserIdAndStatusAndType(receiverUserId, MessageType.STATUS_NOT_READ.getValue(), currentType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendPrivateMessage(MessageDto messageDto, int senderUserId) throws SelectException {
        System.out.println(messageDto.getReceiverUserId());
        userService.getExistUserById(messageDto.getReceiverUserId());
        Message message = new Message();
        MessageText messageText = new MessageText();
        messageText.setSenderUserId(senderUserId);
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageType.TYPE_PRIVATE.getValue());
        messageTextMapper.insertSelective(messageText);
        message.setTextId(messageText.getId());
        message.setReceiverUserId(messageDto.getReceiverUserId());
        message.setReceiveDate(new Date());
        messageMapper.insertSelective(message);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendSystemMessage(MessageDto messageDto) {
        Message message = new Message();
        MessageText messageText = new MessageText();
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageType.TYPE_SYSTEM.getValue());
        messageTextMapper.insertSelective(messageText);
        message.setTextId(messageText.getId());
        message.setReceiverUserId(messageDto.getReceiverUserId());
        message.setReceiveDate(new Date());
        messageMapper.insertSelective(message);
    }

    public void sendSystemMessage(MessageDto messageDto, int textId) {
        Message message = new Message();
        message.setTextId(textId);
        message.setReceiverUserId(messageDto.getReceiverUserId());
        message.setReceiveDate(new Date());
        messageMapper.insertSelective(message);
    }

    public void sendNoticeMessage(MessageDto messageDto, int senderUserId) {
        MessageText messageText = new MessageText();
        messageText.setSenderUserId(senderUserId);
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageType.TYPE_NOTICE.getValue());
        messageText.setPostDate(new Date());
        messageTextMapper.insertSelective(messageText);
    }

    public void sendSystemNoticeMessage(MessageDto messageDto) {
        MessageText messageText = new MessageText();
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageType.TYPE_SYSTEM_NOTICE.getValue());
        messageText.setPostDate(new Date());
        messageTextMapper.insertSelective(messageText);
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

    public void changeMessageStatusByReceiverUserId(int status, int receiverUserId) {
        messageMapper.updateStatusByReceiverUserId(status, receiverUserId);
    }

    public void changeMessageStatusByReceiverUserIdAndSenderUserId(int status, int receiverUserId, int senderUserId) {
        messageMapper.updateStatusByReceiverUserIdAndSenderUserId(status, receiverUserId, senderUserId);
    }

    public boolean isReceiver(int receiverUserId, int id) {
        Message message = messageMapper.selectByPrimaryKey(id);
        return message != null && message.getReceiverUserId().equals(receiverUserId);
    }

    @Async
    public void saveAllNoticeNotReadByReceiverUserId(int receiverUserId) {
        List<MessageText> messageTexts = messageTextMapper.selectAllNoticeNotReadByReceiveUserId(receiverUserId);
        List<Message> messages = new ArrayList<>();
        for (MessageText messageText : messageTexts) {
            Message message = new Message();
            message.setReceiverUserId(receiverUserId);
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
