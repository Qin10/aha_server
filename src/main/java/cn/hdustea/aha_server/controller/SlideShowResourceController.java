package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.entity.SlideShowResource;
import cn.hdustea.aha_server.service.SlideShowResourceService;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/slideShow")
public class SlideShowResourceController {
    @Resource
    private SlideShowResourceService slideShowResourceService;

    /**
     * 获取全部启用的轮播图资源
     */
    @GetMapping()
    @RequiresLogin(requireSignNotice = false)
    public ResponseBean<List<SlideShowResource>> getAllSlideShowResourceEnabled() {
        List<SlideShowResource> slideShowResources = slideShowResourceService.getAllSlideShowResourceByConditions(true);
        return new ResponseBean<>(200, "succ", slideShowResources);
    }
}
