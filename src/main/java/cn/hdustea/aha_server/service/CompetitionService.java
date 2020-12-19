package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.Competition;
import cn.hdustea.aha_server.entity.CompetitionType;
import cn.hdustea.aha_server.mapper.CompetitionMapper;
import cn.hdustea.aha_server.mapper.CompetitionTypeMapper;
import cn.hdustea.aha_server.vo.CompetitionVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 比赛信息的服务类
 *
 * @author STEA_YY
 **/
@Service
public class CompetitionService {
    @Resource
    private CompetitionMapper competitionMapper;
    @Resource
    private CompetitionTypeMapper competitionTypeMapper;

    public List<Competition> getAllCompetition() {
        return competitionMapper.selectAll();
    }

    public List<CompetitionVo> getAllCompetitionVo(){
        return competitionMapper.selectAllVo();
    }

    public Competition getCompetitionById(int id) {
        return competitionMapper.selectByPrimaryKey(id);
    }

    public void saveCompetition(Competition competition) {
        competitionMapper.insertSelective(competition);
    }

    /**
     * 修改竞赛信息
     *
     * @param competition 竞赛信息的实体类
     * @param id          竞赛id
     */
    public void updateCompetitionById(Competition competition, int id) {
        competition.setId(id);
        competitionMapper.updateByPrimaryKeySelective(competition);
    }

    public void deleteCompetitionById(int id) {
        competitionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取所有竞赛标签
     *
     * @return 竞赛标签列表
     */
    public List<CompetitionType> getAllCompetitionType() {
        return competitionTypeMapper.selectAll();
    }

    /**
     * 根据id获取竞赛标签
     *
     * @param id 竞赛标签id
     * @return 竞赛标签实体类
     */
    public CompetitionType getCompetitionTypeById(int id) {
        return competitionTypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 新建竞赛标签
     *
     * @param competitionType 竞赛标签实体类
     */
    public void saveCompetitionType(CompetitionType competitionType) {
        competitionTypeMapper.insertSelective(competitionType);
    }

    /**
     * 修改竞赛标签
     *
     * @param competitionType 竞赛标签实体类
     * @param id             竞赛标签id
     */
    public void updateCompetitionTypeById(CompetitionType competitionType, int id) {
        competitionType.setId(id);
        competitionTypeMapper.updateByPrimaryKeySelective(competitionType);
    }

    /**
     * 删除竞赛标签
     *
     * @param id 竞赛标签id
     */
    public void deleteCompetitionTypeById(int id) {
        competitionTypeMapper.deleteByPrimaryKey(id);
    }

}
