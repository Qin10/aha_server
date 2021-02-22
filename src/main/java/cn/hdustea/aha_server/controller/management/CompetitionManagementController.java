package cn.hdustea.aha_server.controller.management;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.Competition;
import cn.hdustea.aha_server.entity.CompetitionType;
import cn.hdustea.aha_server.service.CompetitionService;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 竞赛后台管理类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin/competition")
public class CompetitionManagementController {
    @Resource
    private CompetitionService competitionService;
    /**
     * 保存竞赛信息
     *
     * @param competition 竞赛信息
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("")
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
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseBean<Object> updateCompetition(@PathVariable("id") int id, @RequestBody Competition competition) {
        competitionService.updateCompetitionById(competition, id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除竞赛信息
     *
     * @param id 竞赛id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseBean<Object> deleteCompetition(@PathVariable("id") int id) {
        competitionService.deleteCompetitionById(id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 保存竞赛标签
     *
     * @param competitionType 竞赛标签
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping("/tag")
    public ResponseBean<Object> saveCompetitionType(@RequestBody @Validated CompetitionType competitionType) {
        competitionService.saveCompetitionType(competitionType);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 根据竞赛标签id更新竞赛标签
     *
     * @param id              竞赛标签id
     * @param competitionType 更新的竞赛标签
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/tag/{id}")
    public ResponseBean<Object> updateCompetitionType(@PathVariable("id") int id, @RequestBody CompetitionType competitionType) {
        competitionService.updateCompetitionTypeById(competitionType, id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除竞赛标签
     *
     * @param id 竞赛标签id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/tag/{id}")
    public ResponseBean<Object> deleteCompetitionType(@PathVariable("id") int id) {
        competitionService.deleteCompetitionTypeById(id);
        return new ResponseBean<>(200, "succ", null);
    }
}
