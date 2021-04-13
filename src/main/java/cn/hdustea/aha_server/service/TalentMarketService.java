package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.UserInfoMapper;
import cn.hdustea.aha_server.mapper.UserMapper;
import cn.hdustea.aha_server.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/4/9
 * @description: 人才市场服务类
 */
@Service
public class TalentMarketService {
    /**
     * 人才市场所需的接口封装在UserInfoMapper中
     */
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private TalentMarketRankService talentMarketRankService;

    /**
     * @param pageNum
     * @param pageSize
     * @return 人才市场列表（无排序）
     * @throws SelectException
     */
    public PageVo<List<UserRoughInfoVo>> getAllTalentMarketRoughPageable(int pageNum, int pageSize) throws SelectException {
        List<UserRoughInfoVo> talentMarketRoughVos;
        PageHelper.startPage(pageNum, pageSize);
        talentMarketRoughVos = userInfoMapper.selectAllTalentMarketRoughVo();
        if(talentMarketRoughVos == null){
            throw new SelectException("信息不存在！");
        }
        PageInfo<UserRoughInfoVo> pageInfo = new PageInfo<>(talentMarketRoughVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    /**
     * @param pageNum
     * @param pageSize
     * @return 人才市场列表（排序）
     * @throws SelectException
     */
//    public PageVo<List<UserContribPointVo>> getAllOrderedTalentMarketRoughPageable(int pageNum, int pageSize) throws SelectException {
//        List<UserContribPointVo> orderedTalentMarketRoughVos;
//        PageHelper.startPage(pageNum, pageSize);
//        orderedTalentMarketRoughVos = userInfoMapper.selectAllTalentMarketRoughVoOrderByContrib();
//        if(orderedTalentMarketRoughVos == null){
//            throw new SelectException("信息不存在！");
//        }
//        PageInfo<UserContribPointVo> pageInfo = new PageInfo<>(orderedTalentMarketRoughVos);
//        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
//    }

    public PageVo<List<UserContribPointVo>> getAllOrderedTalentMarketRoughPageable(int pageNum, int pageSize) throws SelectException {
        List<UserContribPointVo> orderedTalentMarketRoughVos;
        PageHelper.startPage(pageNum, pageSize);
        orderedTalentMarketRoughVos = talentMarketRankService.getTalentMarketRankList();
        if(orderedTalentMarketRoughVos == null){
            throw new SelectException("信息不存在！");
        }
        PageInfo<UserContribPointVo> pageInfo = new PageInfo<>(orderedTalentMarketRoughVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getSize(), pageInfo.getList());
    }

    /**
     * @param id 人才/用户id
     * @return 人才/用户信息
     * @throws SelectException
     */
    public UserTalentMarketDetailVo getUserTalentMarketDetailById(int id) throws SelectException {
        UserTalentMarketDetailVo userTalentMarketDetailVo = userInfoMapper.selectTalentMarketDetailVoByPrimaryKey(id);
        if (userTalentMarketDetailVo == null) {
            throw new SelectException("用户不存在！");
        }
        return userTalentMarketDetailVo;
    }
}
