package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.vo.ResponseBean;
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
    public ResponseBean<List<Competition>> getAllCompetition() {
        List<Competition> competitions = competitionService.getAllCompetition();
        return new ResponseBean<>(200, "succ", competitions);
    }

    /**
     * 根据id获取竞赛标签
     *
     * @param id 竞赛标签id
     */
    @RequiresLogin
    @GetMapping("/tag/{id}")
    public ResponseBean<CompetitionTag> getCompetitionTagById(@PathVariable("id") int id) {
        CompetitionTag competitionTag = competitionService.getCompetitionTagById(id);
        return new ResponseBean<>(200, "succ", competitionTag);
    }

    /**
     * 获取所有竞赛标签
     */
    @RequiresLogin
    @GetMapping("/tag")
    public ResponseBean<List<CompetitionTag>> getAllCompetitionTag() {
        List<CompetitionTag> competitionTags = competitionService.getAllCompetitionTag();
        return new ResponseBean<>(200, "succ", competitionTags);
    }

    /**
     * 保存竞赛信息
     *
     * @param competition 竞赛信息
     */
    @RequiresLogin
    @PostMapping()
    public ResponseBean<Object> saveCompetition(@RequestBody Competition competition) {
        competitionService.saveCompetition(competition);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据竞赛id更新竞赛信息
     *
     * @param id          竞赛id
     * @param competition 更新的竞赛信息
     */
    @RequiresLogin
    @PutMapping("/{id}")
    public ResponseBean<Object> updateCompetition(@PathVariable("id") int id, @RequestBody Competition competition) {
        competitionService.updateCompetitionById(competition, id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 保存竞赛标签
     *
     * @param competitionTag 竞赛标签
     */
    @RequiresLogin
    @PostMapping("/tag")
    public ResponseBean<Object> saveCompetitionTag(@RequestBody @Validated CompetitionTag competitionTag) {
        competitionService.saveCompetitionTag(competitionTag);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据竞赛标签id更新竞赛标签
     *
     * @param id             竞赛标签id
     * @param competitionTag 更新的竞赛标签
     */
    @RequiresLogin
    @PutMapping("/tag/{id}")
    public ResponseBean<Object> updateCompetitionTag(@PathVariable("id") int id, @RequestBody CompetitionTag competitionTag) {
        competitionService.updateCompetitionTagById(competitionTag, id);
        return new ResponseBean<>(200, "succ", null);
    }
}
