package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.constants.MessageConstants;
import cn.hdustea.aha_server.dto.MessageDto;
import cn.hdustea.aha_server.entity.Message;
import cn.hdustea.aha_server.entity.MessageText;
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

    /**
     * 根据用户id按条件分页获取收到的站内信
     *
     * @param receiverUserId 收件人用户id
     * @param pageNum        页码
     * @param pageSize       分页大小
     * @param status         状态
     * @param type           站内信类型
     * @return 站内信列表
     * @throws SelectException 用户未找到异常
     */
    public PageVo<List<MessageVo>> getAllMessageVoByReceiverUserIdPagable(int receiverUserId, int pageNum, int pageSize, Integer status, String type) throws SelectException {
        Integer currentType = null;
        if (status != null && (status < 0 || status > 2)) {
            throw new SelectException("'status'参数取值错误");
        }
        if (type != null) {
            switch (type) {
                case "system": {
                    currentType = MessageConstants.TYPE_SYSTEM;
                    break;
                }
                case "private": {
                    currentType = MessageConstants.TYPE_PRIVATE;
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

    /**
     * 根据站内信id获取站内信实体
     *
     * @param id             站内信id
     * @param receiverUserId 收件人id
     * @return 站内信
     */
    public MessageVo getMessageVoByIdAndReceiverUserId(int id, int receiverUserId) {
        return messageMapper.selectVoByIdAndReceiverUserIdAndNotDeleted(id, receiverUserId);
    }

    /**
     * 根据收发件人id获取会话站内信列表
     *
     * @param receiverUserId 收件人用户id
     * @param senderUserId   发件人用户id
     * @return 站内信列表
     */
    public List<MessageVo> getAllMessageVoInCommunicationBySenderUserIdAndReceiverUserId(int receiverUserId, int senderUserId) {
        return messageMapper.selectAllVoInCommunicationBySenderUserIdAndReceiverUserId(receiverUserId, senderUserId);
    }

    /**
     * 根据用户id和站内信类型获取未读消息数量
     *
     * @param receiverUserId 收件人用户id
     * @param type           站内信类型
     * @return 未读消息数量
     * @throws SelectException 用户未找到异常
     */
    public int getMessageCountNotReadByReceiverUserIdAndType(int receiverUserId, String type) throws SelectException {
        Integer currentType = null;
        if (type != null) {
            switch (type) {
                case "system": {
                    currentType = MessageConstants.TYPE_SYSTEM;
                    break;
                }
                case "private": {
                    currentType = MessageConstants.TYPE_PRIVATE;
                    break;
                }
                default: {
                    throw new SelectException("'type'参数取值错误");
                }
            }
        }
        return messageMapper.countByReceiverUserIdAndStatusAndType(receiverUserId, MessageConstants.STATUS_NOT_READ, currentType);
    }

    /**
     * 发送私信
     *
     * @param messageDto   站内信
     * @param senderUserId 发件人用户id
     * @throws SelectException 用户未找到异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendPrivateMessage(MessageDto messageDto, int senderUserId) throws SelectException {
        System.out.println(messageDto.getReceiverUserId());
        userService.getExistUserVoById(messageDto.getReceiverUserId());
        Message message = new Message();
        MessageText messageText = new MessageText();
        messageText.setSenderUserId(senderUserId);
        messageText.setTitle(messageDto.getTitle());
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageConstants.TYPE_PRIVATE);
        messageTextMapper.insertSelective(messageText);
        message.setTextId(messageText.getId());
        message.setReceiverUserId(messageDto.getReceiverUserId());
        message.setReceiveDate(new Date());
        messageMapper.insertSelective(message);
    }

    /**
     * 发送系统私信
     *
     * @param messageDto 站内信
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendSystemMessage(MessageDto messageDto) {
        Message message = new Message();
        MessageText messageText = new MessageText();
        messageText.setTitle(messageDto.getTitle());
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageConstants.TYPE_SYSTEM);
        messageTextMapper.insertSelective(messageText);
        message.setTextId(messageText.getId());
        message.setReceiverUserId(messageDto.getReceiverUserId());
        message.setReceiveDate(new Date());
        messageMapper.insertSelective(message);
    }

    /**
     * 根据信件内容id发送系统站内信
     *
     * @param messageDto 站内信
     * @param textId     信件内容id
     */
    public void sendSystemMessage(MessageDto messageDto, int textId) {
        Message message = new Message();
        message.setTextId(textId);
        message.setReceiverUserId(messageDto.getReceiverUserId());
        message.setReceiveDate(new Date());
        messageMapper.insertSelective(message);
    }

    /**
     * 发送广播站内信
     *
     * @param messageDto   站内信
     * @param senderUserId 发件人用户id
     */
    public void sendNoticeMessage(MessageDto messageDto, int senderUserId) {
        MessageText messageText = new MessageText();
        messageText.setSenderUserId(senderUserId);
        messageText.setTitle(messageDto.getTitle());
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageConstants.TYPE_NOTICE);
        messageText.setPostDate(new Date());
        messageTextMapper.insertSelective(messageText);
    }

    /**
     * 发送系统广播站内信
     *
     * @param messageDto 站内信
     */
    public void sendSystemNoticeMessage(MessageDto messageDto) {
        MessageText messageText = new MessageText();
        messageText.setTitle(messageDto.getTitle());
        messageText.setContent(messageDto.getContent());
        messageText.setType(MessageConstants.TYPE_SYSTEM_NOTICE);
        messageText.setPostDate(new Date());
        messageTextMapper.insertSelective(messageText);
    }

    /**
     * 根据id更新通知内容
     *
     * @param messageDto 站内信
     * @param id         站内信内容id
     */
    public void updateNoticeTextById(MessageDto messageDto, int id) {
        MessageText messageText = new MessageText();
        messageText.setId(id);
        messageText.setContent(messageDto.getContent());
        messageTextMapper.updateByPrimaryKeySelective(messageText);
    }

    /**
     * 根据id更改站内信状态
     *
     * @param status 状态
     * @param id     站内信id
     */
    public void changeMessageStatusById(int status, int id) {
        messageMapper.updateStatusById(status, id);
    }

    /**
     * 根据收件人用户id批量修改站内信状态
     *
     * @param status         状态
     * @param receiverUserId 收件人用户id
     */
    public void changeMessageStatusByReceiverUserId(int status, int receiverUserId) {
        messageMapper.updateStatusByReceiverUserId(status, receiverUserId);
    }

    /**
     * 根据收件人和发件人用户id修改站内信状态
     *
     * @param status         状态
     * @param receiverUserId 收件人用户id
     * @param senderUserId   发件人用户id
     */
    public void changeMessageStatusByReceiverUserIdAndSenderUserId(int status, int receiverUserId, int senderUserId) {
        messageMapper.updateStatusByReceiverUserIdAndSenderUserId(status, receiverUserId, senderUserId);
    }

    /**
     * 校验用户是否是站内信收件人
     *
     * @param receiverUserId 收件人用户id
     * @param id             站内信id
     * @return 校验结果
     */
    public boolean isReceiver(int receiverUserId, int id) {
        Message message = messageMapper.selectByPrimaryKey(id);
        return message != null && message.getReceiverUserId().equals(receiverUserId);
    }

    /**
     * 根据收件人用户id异步获取未读广播消息
     *
     * @param receiverUserId 收件人id
     */
    @Async
    public void saveAllNoticeNotReadByReceiverUserId(int receiverUserId) {
        List<MessageText> messageTexts = messageTextMapper.selectAllNoticeNotReadByReceiveUserId(receiverUserId);
        List<Message> messages = new ArrayList<>();
        for (MessageText messageText : messageTexts) {
            Message message = new Message();
            message.setReceiverUserId(receiverUserId);
            message.setTextId(messageText.getId());
            message.setReceiveDate(messageText.getPostDate());
            message.setStatus(MessageConstants.STATUS_NOT_READ);
            messages.add(message);
        }
        if (!messages.isEmpty()) {
            messageMapper.insertList(messages);
        }
    }

}
