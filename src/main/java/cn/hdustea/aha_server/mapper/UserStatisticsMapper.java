package cn.hdustea.aha_server.mapper;
import cn.hdustea.aha_server.entity.UserStatistics;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * ${description}
 *
 * @author STEA_YY
 **/
public interface UserStatisticsMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserStatistics record);

    int insertSelective(UserStatistics record);

    UserStatistics selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserStatistics record);

    int updateByPrimaryKey(UserStatistics record);

    int updateIncTotalContribPointByUserId(@Param("addedTotalContribPoint")BigDecimal addedTotalContribPoint,@Param("userId")Integer userId);

    int updateDecTotalContribPointByUserId(@Param("deductedTotalContribPoint")BigDecimal deductedTotalContribPoint,@Param("userId")Integer userId);

	int updateIncTotalProjectByUserId(@Param("addedTotalProject")Integer addedTotalProject,@Param("userId")Integer userId);

	int updateDecTotalProjectByUserId(@Param("deductedTotalProject")Integer deductedTotalProject,@Param("userId")Integer userId);

	int updateIncTotalReceivedCollectionByUserId(@Param("addedTotalReceivedCollection")Integer addedTotalReceivedCollection,@Param("userId")Integer userId);

	int updateDecTotalReceivedCollectionByUserId(@Param("deductedTotalReceivedCollection")Integer deductedTotalReceivedCollection,@Param("userId")Integer userId);

	int updateIncTotalPurchasedCountByUserId(@Param("addedTotalPurchasedCount")Integer addedTotalPurchasedCount,@Param("userId")Integer userId);

    int updateDecTotalPurchasedCountByUserId(@Param("deductedTotalPurchasedCount")Integer deductedTotalPurchasedCount,@Param("userId")Integer userId);


}