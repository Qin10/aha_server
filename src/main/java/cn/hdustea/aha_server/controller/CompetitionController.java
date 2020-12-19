package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.Competition;
import cn.hdustea.aha_server.entity.CompetitionType;
import cn.hdustea.aha_server.service.CompetitionService;
import cn.hdustea.aha_server.vo.CompetitionVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 竞赛信息和竞赛标签相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/competition")
public class CompetitionController {
    @Resource
    private CompetitionService competitionService;

    /**
     * 根据id获取竞赛信息
     *
     * @param id 竞赛信息id
     */
    @RequiresLogin
    @GetMapping("/{id}")
    public ResponseBean<Competition> getCompetitionById(@PathVariable("id") int id) {
        Competition competition = competitionService.getCompetitionById(id);
        return new ResponseBean<>(200, "succ", competition);
    }

    /**
     * 获取所有竞赛信息
     */
    @RequiresLogin
    @GetMapping()
    public ResponseBean<List<CompetitionVo>> getAllCompetition() {
        List<CompetitionVo> competitionVos = competitionService.getAllCompetitionVo();
        return new ResponseBean<>(200, "succ", competitionVos);
    }

    /**
     * 根据id获取竞赛类别
     *
     * @param id 竞赛类别id
     */
    @RequiresLogin
    @GetMapping("/type/{id}")
    public ResponseBean<CompetitionType> getCompetitionTypeById(@PathVariable("id") int id) {
        CompetitionType competitionType = competitionService.getCompetitionTypeById(id);
        return new ResponseBean<>(200, "succ", competitionType);
    }

    /**
     * 获取所有竞赛类别
     */
    @RequiresLogin
    @GetMapping("/type")
    public ResponseBean<List<CompetitionType>> getAllCompetitionType() {
        List<CompetitionType> competitionTypes = competitionService.getAllCompetitionType();
        return new ResponseBean<>(200, "succ", competitionTypes);
    }
}
