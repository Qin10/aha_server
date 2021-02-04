package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.UserStatistics;
import cn.hdustea.aha_server.mapper.UserStatisticsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 用户统计信息服务类
 *
 * @author STEA_YY
 **/
@Service
public class UserStatisticsService {
    @Resource
    private UserStatisticsMapper userStatisticsMapper;

    public UserStatistics getUserStatisticsByUserId(int userId) {
        return userStatisticsMapper.selectByPrimaryKey(userId);
    }

    public void saveUserStatistics(UserStatistics userStatistics) {
        userStatisticsMapper.insertSelective(userStatistics);
    }

    public void incrTotalContribPointByUserId(BigDecimal contribPoint, int userId) {
        userStatisticsMapper.updateIncTotalContribPointByUserId(contribPoint, userId);
    }

    public void decTotalContribPointByUserId(BigDecimal contribPoint, int userId) {
        userStatisticsMapper.updateDecTotalContribPointByUserId(contribPoint, userId);
    }

    public void incrTotalProjectByUserId(Integer projectCount, int userId) {
        userStatisticsMapper.updateIncTotalProjectByUserId(projectCount, userId);
    }

    public void decTotalProjectByUserId(Integer projectCount, int userId) {
        userStatisticsMapper.updateDecTotalProjectByUserId(projectCount, userId);
    }

    public void incrTotalReceivedCollectionByUserId(Integer receivedCollectionCount, int userId) {
        userStatisticsMapper.updateIncTotalReceivedCollectionByUserId(receivedCollectionCount, userId);
    }

    public void decTotalReceivedCollectionByUserId(Integer receivedCollectionCount, int userId) {
        userStatisticsMapper.updateDecTotalReceivedCollectionByUserId(receivedCollectionCount, userId);
    }

    public void incrTotalTotalPurchasedCountByUserId(Integer purchasedCount, int userId) {
        userStatisticsMapper.updateIncTotalPurchasedCountByUserId(purchasedCount, userId);
    }

    public void decTotalTotalPurchasedCountByUserId(Integer purchasedCount, int userId) {
        userStatisticsMapper.updateDecTotalPurchasedCountByUserId(purchasedCount, userId);
    }
}
