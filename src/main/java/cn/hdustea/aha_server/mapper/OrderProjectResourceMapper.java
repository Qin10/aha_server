package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.OrderProjectResource;
import org.apache.ibatis.annotations.Param;

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
}