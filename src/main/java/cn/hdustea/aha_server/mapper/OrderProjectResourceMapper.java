package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.OrderProjectResource;
import cn.hdustea.aha_server.vo.OrderProjectResourceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* ${description}
*
* @author STEA_YY
**/
public interface OrderProjectResourceMapper {
    int deleteByPrimaryKey(@Param("orderId") Integer orderId, @Param("resourceId") Integer resourceId);

    int insert(OrderProjectResource record);

    int insertSelective(OrderProjectResource record);

    OrderProjectResource selectByPrimaryKey(@Param("orderId") Integer orderId, @Param("resourceId") Integer resourceId);

    int updateByPrimaryKeySelective(OrderProjectResource record);

    int updateByPrimaryKey(OrderProjectResource record);

    List<OrderProjectResource> selectAllByOrderId(@Param("orderId")Integer orderId);

    List<OrderProjectResourceVo> selectAllVoByOrderId(@Param("orderId")Integer orderId);
}