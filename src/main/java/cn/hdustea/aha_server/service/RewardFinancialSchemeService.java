package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.RewardFinancialScheme;
import cn.hdustea.aha_server.mapper.RewardFinancialSchemeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 奖励财务计划服务类
 *
 * @author STEA_YY
 **/
@Service
public class RewardFinancialSchemeService {
    @Resource
    private RewardFinancialSchemeMapper rewardFinancialSchemeMapper;

    public RewardFinancialScheme getRewardFinancialSchemeById(int id) {
        return rewardFinancialSchemeMapper.selectByPrimaryKey(id);
    }
}
