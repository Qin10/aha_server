package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.MessageText;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface MessageTextMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageText record);

    int insertSelective(MessageText record);

    MessageText selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageText record);

    int updateByPrimaryKey(MessageText record);

    List<MessageText> selectAllNoticeNotReadByReceivePhone(@Param("receivePhone") String receivePhone);

}