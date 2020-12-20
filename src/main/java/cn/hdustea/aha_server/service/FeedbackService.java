package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.FeedbackAdminDto;
import cn.hdustea.aha_server.dto.FeedbackUserDto;
import cn.hdustea.aha_server.entity.Feedback;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.mapper.FeedbackMapper;
import cn.hdustea.aha_server.vo.FeedbackVo;
import cn.hdustea.aha_server.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 反馈服务类
 *
 * @author STEA_YY
 **/
@Service
public class FeedbackService {
    @Resource
    private FeedbackMapper feedbackMapper;

    public void saveFeedbackByUserId(FeedbackUserDto feedbackUserDto, int userId) {
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(feedbackUserDto, feedback);
        feedback.setUserId(userId);
        feedback.setTime(new Date());
        feedback.setStatus(0);
        feedbackMapper.insertSelective(feedback);
    }

    public void replyFeedbackById(FeedbackAdminDto feedbackAdminDto, int id) {
        Feedback feedback = feedbackMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(feedbackAdminDto, feedback);
        feedback.setReplyTime(new Date());
    }

    public PageVo<List<FeedbackVo>> getAllFeedbackVoPagable(int pageNum, int pageSize, Integer status, Integer type, Integer userId, Integer lowestLevel, Integer highestLevel, String sortBy, String orderBy) throws SelectException {
        String currentSortBy;
        String currentOrderBy;
        switch (sortBy) {
            case "time": {
                currentSortBy = "fe_time";
                break;
            }
            case "type": {
                currentSortBy = "fe_type";
                break;
            }
            case "status": {
                currentSortBy = "fe_status";
                break;
            }
            case "replyTime": {
                currentSortBy = "fe_reply_time";
                break;
            }
            case "level": {
                currentSortBy = "fe_level";
                break;
            }
            default: {
                throw new SelectException("'sortBy'参数取值错误！");
            }
        }
        switch (orderBy) {
            case "desc": {
                currentOrderBy = "desc";
                break;
            }
            case "asc": {
                currentOrderBy = "asc";
                break;
            }
            default: {
                throw new SelectException("'orderBy'参数取值错误！");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy(currentSortBy + " " + currentOrderBy);
        List<FeedbackVo> feedbackVos = feedbackMapper.selectAllVoByConditions(type, status, userId, lowestLevel, highestLevel);
        PageInfo<FeedbackVo> pageInfo = new PageInfo<>(feedbackVos);
        return new PageVo<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
    }
}
