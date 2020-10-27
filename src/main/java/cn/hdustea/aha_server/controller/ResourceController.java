package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.OssPolicyBean;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.Resource;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.ResourceService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 竞赛资源的控制类
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @javax.annotation.Resource
    private ResourceService resourceService;
    @javax.annotation.Resource
    private OssService ossService;

    @RequiresLogin
    @GetMapping("/{id}")
    public ResponseBean getResourceById(@PathVariable("id") int id) {
        Resource resource = resourceService.getResourceById(id);
        return new ResponseBean(200, "succ", resource, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @GetMapping("/sign/upload")
    public ResponseBean signUploadFile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        OssPolicyBean ossPolicyBean = ossService.signUpload("resource/" + phone);
        return new ResponseBean(200, "succ", ossPolicyBean, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PostMapping()
    public ResponseBean saveResource(HttpServletRequest request, @RequestBody Resource resource) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        resourceService.saveResourceAndAuthor(resource, phone);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PutMapping("/{id}")
    public ResponseBean updateResourceById(HttpServletRequest request, @RequestBody Resource resource, @PathVariable("id") int id) throws UpdateException, PermissionDeniedException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        if (!resourceService.hasPermission(phone, id)) {
            throw new PermissionDeniedException();
        }
        resource.setId(id);
        resourceService.updateResource(resource);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @DeleteMapping("/{id}")
    public ResponseBean deleteResourceById(HttpServletRequest request, @PathVariable("id") int id) throws PermissionDeniedException, DeleteException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        if (!resourceService.hasPermission(phone, id)) {
            throw new PermissionDeniedException();
        }
        resourceService.deleteResourceById(id);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

}
