package cn.hdustea.aha_server.controller.management;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.NoticeDto;
import cn.hdustea.aha_server.entity.Notice;
import cn.hdustea.aha_server.service.NoticeService;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通知后台管理类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin/notice")
public class NoticeManagementController {
    @Resource
    private NoticeService noticeService;
    /**
     * 获取全部公告
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping()
    public ResponseBean<List<Notice>> getAllNotice() {
        List<Notice> notices = noticeService.getAllNotice(null, null);
        return new ResponseBean<>(200, "succ", notices);
    }

    /**
     * 发布公告
     *
     * @param noticeDto 公告
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping()
    public ResponseBean<Object> sendNotice(@RequestBody @Validated NoticeDto noticeDto) {
        noticeService.saveNotice(noticeDto);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 修改公告
     *
     * @param noticeDto 公告
     * @param noticeId  公告id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/{noticeId}")
    public ResponseBean<Object> sendNotice(@RequestBody @Validated NoticeDto noticeDto, @PathVariable("noticeId") Integer noticeId) {
        noticeService.updateNoticeById(noticeDto, noticeId);
        return new ResponseBean<>(200, "succ", null);
    }
}
