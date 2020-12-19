package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.Notice;
import cn.hdustea.aha_server.service.NoticeService;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    /**
     * 获取投放的系统公告
     */
    @RequiresLogin
    @GetMapping
    public ResponseBean<List<Notice>> getAllNoticeEnabled() {
        List<Notice> notices = noticeService.getAllNoticeEnabled();
        return new ResponseBean<>(200, "succ", notices);
    }
}
