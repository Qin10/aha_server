package cn.hdustea.aha_server.mapper;

import cn.hdustea.aha_server.entity.PurchasedResource;
import cn.hdustea.aha_server.vo.PurchasedResourceManagementVo;
import cn.hdustea.aha_server.vo.PurchasedResourceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface PurchasedResourceMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("resourceId") Integer resourceId);

    int insert(PurchasedResource record);

    int insertSelective(PurchasedResource record);

    PurchasedResource selectByPrimaryKey(@Param("userId") Integer userId, @Param("resourceId") Integer resourceId);

    List<PurchasedResource> selectAllByResourceId(@Param("resourceId")Integer resourceId);

    int updateByPrimaryKeySelective(PurchasedResource record);

    int updateByPrimaryKey(PurchasedResource record);

    List<PurchasedResource> selectAllByUserId(@Param("userId") Integer userId);

    List<PurchasedResourceVo> selectAllVoByUserId(@Param("userId") Integer userId);

    List<PurchasedResourceManagementVo> selectAllManagementVoByConditions(@Param("resourceId") Integer resourceId, @Param("projectId") Integer projectId, @Param("userId") Integer userId, @Param("orderId") Integer orderId);

    List<PurchasedResource> selectAllByProjectIdAndUserId(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

    List<Integer> selectAllResourceIdByProjectIdAndUserId(@Param("projectId") Integer projectId, @Param("userId") Integer userId);
}