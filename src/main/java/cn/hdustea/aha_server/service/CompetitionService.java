package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.mapper.CompetitionMapper;
import cn.hdustea.aha_server.mapper.CompetitionTagMapper;
import cn.hdustea.aha_server.entity.Competition;
import cn.hdustea.aha_server.entity.CompetitionTag;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
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
    private CompetitionTagMapper competitionTagMapper;

    public List<Competition> getAllCompetition() {
        return competitionMapper.selectAll();
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
     * @throws UpdateException 修改失败异常
     */
    public void updateCompetition(Competition competition) throws UpdateException {
        if (competitionMapper.selectByPrimaryKey(competition.getId()) == null) {
            throw new UpdateException();
        }
        competitionMapper.updateByPrimaryKeySelective(competition);
    }

    /**
     * 获取所有竞赛标签
     *
     * @return 竞赛标签列表
     */
    public List<CompetitionTag> getAllCompetitionTag() {
        return competitionTagMapper.selectAll();
    }

    /**
     * 根据id获取竞赛标签
     *
     * @param id 竞赛标签id
     * @return 竞赛标签实体类
     */
    public CompetitionTag getCompetitionTagById(int id) {
        return competitionTagMapper.selectByPrimaryKey(id);
    }

    /**
     * 新建竞赛标签
     *
     * @param competitionTag 竞赛标签实体类
     */
    public void saveCompetitionTag(CompetitionTag competitionTag) {
        competitionTagMapper.insertSelective(competitionTag);
    }

    /**
     * 修改竞赛标签
     *
     * @param competitionTag 竞赛标签实体类
     * @throws UpdateException 修改失败异常
     */
    public void updateCompetitionTag(CompetitionTag competitionTag) throws UpdateException {
        if (competitionTagMapper.selectByPrimaryKey(competitionTag.getId()) == null) {
            throw new UpdateException();
        }
        competitionTagMapper.updateByPrimaryKeySelective(competitionTag);
    }

}
