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

    /**
     * 按条件获取全部公告
     *
     * @param enable      是否启用
     * @param currentTime 当前时间
     * @return 公告列表
     */
    public List<Notice> getAllNotice(Boolean enable, Date currentTime) {
        return noticeMapper.selectAllByConditions(enable, currentTime);
    }

    /**
     * 保存(发布)公告
     *
     * @param noticeDto 公告
     */
    public void saveNotice(NoticeDto noticeDto) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDto, notice);
        notice.setCreateTime(new Date());
        noticeMapper.insertSelective(notice);
    }

    /**
     * 根据id修改公告
     *
     * @param noticeDto 公告
     * @param id        公告id
     */
    public void updateNoticeById(NoticeDto noticeDto, int id) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDto, notice);
        notice.setId(id);
        noticeMapper.updateByPrimaryKeySelective(notice);
    }
}
