package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.Competition;
import cn.hdustea.aha_server.entity.CompetitionTag;
import cn.hdustea.aha_server.service.CompetitionService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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

    @RequiresLogin
    @GetMapping("/{id}")
    public ResponseBean getCompetitionById(@PathVariable("id") int id) {
        Competition competition = competitionService.getCompetitionById(id);
        return new ResponseBean(200, "succ", competition, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @GetMapping("/tag/{id}")
    public ResponseBean getCompetitionTagById(@PathVariable("id") int id) {
        CompetitionTag competitionTag = competitionService.getCompetitionTagById(id);
        return new ResponseBean(200, "succ", competitionTag, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PostMapping()
    public ResponseBean saveCompetition(@RequestBody Competition competition) {
        competitionService.saveCompetition(competition);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PostMapping("/tag")
    public ResponseBean saveCompetitionTag(@RequestBody CompetitionTag competitionTag) {
        competitionService.saveCompetitionTag(competitionTag);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }
}
