package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.ContribPointOrder;
import cn.hdustea.aha_server.vo.ContribPointOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 贡献点订单表mapper
 *
 * @author STEA_YY
 **/
public interface ContribPointOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContribPointOrder record);

    int insertSelective(ContribPointOrder record);

    ContribPointOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContribPointOrder record);

    int updateByPrimaryKey(ContribPointOrder record);

    ContribPointOrderVo selectVoByPrimaryKey(Integer id);

    List<ContribPointOrder> selectAllByUserId(@Param("userId") Integer userId);

    List<ContribPointOrderVo> selectAllVoByUserId(@Param("userId") Integer userId);
}