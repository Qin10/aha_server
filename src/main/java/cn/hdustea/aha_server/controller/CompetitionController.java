package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.Competition;
import cn.hdustea.aha_server.entity.CompetitionTag;
import cn.hdustea.aha_server.service.CompetitionService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 竞赛信息的控制类
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/competition")
public class CompetitionController {
    @Resource
    private CompetitionService competitionService;

    /**
     * 根据id获取竞赛信息的接口
     *
     * @param id 竞赛信息id
     */
    @RequiresLogin
    @GetMapping("/{id}")
    public ResponseBean getCompetitionById(@PathVariable("id") int id) {
        Competition competition = competitionService.getCompetitionById(id);
        return new ResponseBean(200, "succ", competition, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 获取所有竞赛信息（列表）的接口
     */
    @RequiresLogin
    @GetMapping()
    public ResponseBean getAllCompetition() {
        List<Competition> competitions = competitionService.getAllCompetition();
        return new ResponseBean(200, "succ", competitions, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 根据id获取竞赛标签信息的接口
     *
     * @param id 竞赛标签id
     */
    @RequiresLogin
    @GetMapping("/tag/{id}")
    public ResponseBean getCompetitionTagById(@PathVariable("id") int id) {
        CompetitionTag competitionTag = competitionService.getCompetitionTagById(id);
        return new ResponseBean(200, "succ", competitionTag, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 获取所有竞赛标签（列表）的接口
     */
    @RequiresLogin
    @GetMapping("/tag")
    public ResponseBean getAllCompetitionTag() {
        List<CompetitionTag> competitionTags = competitionService.getAllCompetitionTag();
        return new ResponseBean(200, "succ", competitionTags, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 保存竞赛信息的接口
     *
     * @param competition 竞赛信息的实体类
     */
    @RequiresLogin
    @PostMapping()
    public ResponseBean saveCompetition(@RequestBody Competition competition) {
        competitionService.saveCompetition(competition);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 保存竞赛标签的接口
     *
     * @param competitionTag 竞赛标签的实体类
     */
    @RequiresLogin
    @PostMapping("/tag")
    public ResponseBean saveCompetitionTag(@RequestBody @Validated CompetitionTag competitionTag) {
        competitionService.saveCompetitionTag(competitionTag);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }
}
