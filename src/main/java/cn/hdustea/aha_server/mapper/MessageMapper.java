package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Message;
import cn.hdustea.aha_server.vo.MessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 站内信记录表mapper
 *
 * @author STEA_YY
 **/
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    int updateStatusById(@Param("updatedStatus") Integer updatedStatus, @Param("id") Integer id);

    int updateStatusByReceiverUserId(@Param("updatedStatus") Integer updatedStatus, @Param("receiverUserId") Integer receiverUserId);

    int updateStatusByReceiverUserIdAndSenderUserId(@Param("updatedStatus") Integer updatedStatus, @Param("receiverUserId") Integer receiverUserId, @Param("senderUserId") Integer senderUserId);

    List<MessageVo> selectAllVoByConditions(@Param("receiverUserId") Integer receiverUserId, @Param("status") Integer status, @Param("type") Integer type);

    int insertList(@Param("list") List<Message> list);

    MessageVo selectVoByIdAndReceiverUserIdAndNotDeleted(@Param("id") Integer id, @Param("receiverUserId") Integer receiverUserId);

    Integer countByReceiverUserIdAndStatusAndType(@Param("receiverUserId") Integer receiverUserId, @Param("status") Integer status, @Param("type") Integer type);

    List<MessageVo> selectAllVoInCommunicationBySenderUserIdAndReceiverUserId(@Param("receiverUserId") Integer receiverUserId, @Param("senderUserId") Integer senderUserId);
}