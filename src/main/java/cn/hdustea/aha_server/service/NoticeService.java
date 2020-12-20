package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.dto.NoticeDto;
import cn.hdustea.aha_server.entity.Notice;
import cn.hdustea.aha_server.mapper.NoticeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 公告服务类
 *
 * @author STEA_YY
 **/
@Service
public class NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    public List<Notice> getAllNotice(Boolean enable, Date currentTime) {
        return noticeMapper.selectAllByConditions(enable, currentTime);
    }

    public void saveNotice(NoticeDto noticeDto) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDto, notice);
        notice.setCreateTime(new Date());
        noticeMapper.insertSelective(notice);
    }

    public void updateNoticeById(NoticeDto noticeDto, int id) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDto, notice);
        notice.setId(id);
        noticeMapper.updateByPrimaryKeySelective(notice);
    }
}
