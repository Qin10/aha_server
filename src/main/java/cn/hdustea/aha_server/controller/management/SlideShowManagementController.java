package cn.hdustea.aha_server.controller.management;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.dto.SlideShowResourceDto;
import cn.hdustea.aha_server.entity.SlideShowResource;
import cn.hdustea.aha_server.service.SlideShowResourceService;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图后台管理类请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/admin/slideShow")
public class SlideShowManagementController {
    @Resource
    private SlideShowResourceService slideShowResourceService;

    /**
     * 获取指定轮播图资源
     *
     * @param id 轮播图资源id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseBean<SlideShowResource> getSlideShowResourceById(@PathVariable("id") int id) {
        SlideShowResource slideShowResource = slideShowResourceService.getSlideShowResourceById(id);
        return new ResponseBean<>(200, "succ", slideShowResource);
    }

    /**
     * 按条件获取全部轮播图资源
     *
     * @param enabled 是否启用
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @GetMapping()
    public ResponseBean<List<SlideShowResource>> getAllSlideShowResourceEnabled(@RequestParam(required = false) Boolean enabled) {
        List<SlideShowResource> slideShowResources = slideShowResourceService.getAllSlideShowResourceByConditions(enabled);
        return new ResponseBean<>(200, "succ", slideShowResources);
    }

    /**
     * 新建轮播图资源
     *
     * @param slideShowResourceDto 轮播图资源信息
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PostMapping()
    public ResponseBean<Object> saveSlideShowResource(@Validated @RequestBody SlideShowResourceDto slideShowResourceDto) {
        slideShowResourceService.saveSlideShowResource(slideShowResourceDto);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 更新轮播图资源
     *
     * @param slideShowResourceDto 轮播图资源信息
     * @param id                   轮播图资源id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseBean<Object> updateSlideShowResourceById(@RequestBody SlideShowResourceDto slideShowResourceDto, @PathVariable("id") int id) {
        slideShowResourceService.updateSlideShowResourceById(slideShowResourceDto, id);
        return new ResponseBean<>(200, "succ", null);
    }

    /**
     * 删除轮播图资源
     *
     * @param id 轮播图资源id
     */
    @RequiresLogin(requiresRoles = "ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseBean<Object> deleteSlideShowResourceById(@PathVariable("id") int id) {
        slideShowResourceService.deleteSlideShowResourceById(id);
        return new ResponseBean<>(200, "succ", null);
    }
}
