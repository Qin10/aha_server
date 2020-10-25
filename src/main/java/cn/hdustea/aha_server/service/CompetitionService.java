package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dao.CompetitionMapper;
import cn.hdustea.aha_server.dao.CompetitionTagMapper;
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

    public void updateCompetition(Competition competition) throws UpdateException {
        if (competitionMapper.selectByPrimaryKey(competition.getId()) == null) {
            throw new UpdateException();
        }
        competitionMapper.updateByPrimaryKeySelective(competition);
    }

    public List<CompetitionTag> getAllCompetitionTag() {
        return competitionTagMapper.selectAll();
    }

    public CompetitionTag getCompetitionTagById(int id) {
        return competitionTagMapper.selectByPrimaryKey(id);
    }

    public void saveCompetitionTag(CompetitionTag competitionTag) {
        competitionTagMapper.insertSelective(competitionTag);
    }

    public void updateCompetitionTag(CompetitionTag competitionTag) throws UpdateException {
        if (competitionTagMapper.selectByPrimaryKey(competitionTag.getId()) == null) {
            throw new UpdateException();
        }
        competitionTagMapper.updateByPrimaryKeySelective(competitionTag);
    }

}
