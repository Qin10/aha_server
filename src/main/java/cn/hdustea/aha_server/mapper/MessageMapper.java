package cn.hdustea.aha_server.mapper;
import cn.hdustea.aha_server.entity.Message;
import cn.hdustea.aha_server.vo.MessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
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

    int updateStatusById(@Param("updatedStatus")Integer updatedStatus,@Param("id")Integer id);

    int updateStatusByReceiverPhone(@Param("updatedStatus")Integer updatedStatus,@Param("receiverPhone")String receiverPhone);

    List<MessageVo> selectAllVoByReceiverPhone(@Param("receiverPhone")String receiverPhone);

    int insertList(@Param("list")List<Message> list);

    MessageVo selectVoByIdAndReceiverPhoneAndNotDeleted(@Param("id")Integer id,@Param("receiverPhone")String receiverPhone);


}