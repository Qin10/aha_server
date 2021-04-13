package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.TalentMarketService;
import cn.hdustea.aha_server.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/4/10
 * @description: 人才市场信息相关请求
 */
@RequestMapping("/talentMarket")
@RestController
public class TalentMarketController {
    @Resource
    private TalentMarketService talentMarketService;

    /**
     * @param pageNum
     * @param pageSize
     * @return 分页人才市场页面粗略信息（根据贡献点排序）
     * @throws SelectException
     */
    @RequiresLogin
    @GetMapping()
    public ResponseBean<PageVo<List<UserContribPointVo>>> getAllTalentMarketRoughVo(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize) throws SelectException {
        PageVo<List<UserContribPointVo>> talentMarketRoughVos = talentMarketService.getAllOrderedTalentMarketRoughPageable(pageNum, pageSize);
        return new ResponseBean<>(200, "succ", talentMarketRoughVos);
    }

    /**
     * @param userId
     * @return  人才市场指定userId详细信息
     * @throws SelectException
     */
    @RequiresLogin
    @GetMapping("/{userId}")
    public ResponseBean<UserTalentMarketDetailVo> getTalentMarketDetailById(@PathVariable("userId") int userId) throws SelectException {
        UserTalentMarketDetailVo userTalentMarketDetailVo = talentMarketService.getUserTalentMarketDetailById(userId);
        return new ResponseBean<>(200, "succ", userTalentMarketDetailVo);
    }
}
