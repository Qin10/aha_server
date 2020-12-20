package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.Feedback;
import cn.hdustea.aha_server.vo.FeedbackVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface FeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);

    List<FeedbackVo> selectAllVoByConditions(@Param("type") Integer type, @Param("status") Integer status, @Param("userId") Integer userId, @Param("lowestLevel") Integer lowestLevel, @Param("highestLevel") Integer highestLevel);
}