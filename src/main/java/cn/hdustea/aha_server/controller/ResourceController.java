package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.OssPolicyBean;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.entity.Resource;
import cn.hdustea.aha_server.entity.ResourceInfo;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.ResourceInfoService;
import cn.hdustea.aha_server.service.ResourceService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

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
    private ResourceInfoService resourceInfoService;
    @javax.annotation.Resource
    private OssService ossService;

    /**
     * 根据id获取资源的接口
     *
     * @param id 资源id
     */
    @RequiresLogin
    @GetMapping("/{id}")
    public ResponseBean getResourceById(@PathVariable("id") int id) {
        Resource resource = resourceService.getResourceById(id);
        return new ResponseBean(200, "succ", resource, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 获取oss上传签名的接口，上传后的资源的读写权限均为私有
     */
    @RequiresLogin(requireSignContract = true)
    @GetMapping("/sign/upload")
    public ResponseBean signUploadFile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        OssPolicyBean ossPolicyBean = ossService.signUpload("resource/" + phone, true);
        return new ResponseBean(200, "succ", ossPolicyBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 创建新资源的接口
     *
     * @param resource 资源的实体类
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping()
    public ResponseBean saveResource(HttpServletRequest request, @RequestBody @Validated Resource resource) {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        resourceService.saveResourceAndAuthor(resource, phone);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 根据id修改资源的接口，只有资源的拥有者有请求权限
     *
     * @param resource 修改后的资源实体类
     * @param id       要修改的资源的id
     * @throws UpdateException           修改失败异常
     * @throws PermissionDeniedException 无操作资源权限的异常
     */
    @RequiresLogin(requireSignContract = true)
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

    /**
     * 根据id删除资源的接口，只有资源的拥有者有请求权限
     *
     * @param id 要删除的资源的id
     * @throws PermissionDeniedException 无操作资源权限的异常
     * @throws DeleteException           删除失败异常
     */
    @RequiresLogin(requireSignContract = true)
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

    @RequiresLogin
    @GetMapping("/sign/download/{id}")
    public ResponseBean signDownloadResourceByid(HttpServletRequest request, @PathVariable("id") int id) throws SelectException {
        String url = resourceService.signDownloadResourceByid(id);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("url", url);
        return new ResponseBean(200, "succ", responseMap, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @GetMapping("/info/{id}")
    public ResponseBean getResourceInfoById(@PathVariable("id") int id) {
        ResourceInfo resourceInfo = resourceInfoService.getResourceInfoByResourceId(id);
        return new ResponseBean(200, "succ", resourceInfo, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PutMapping("/info/{id}")
    public ResponseBean updateResourceInfoById(HttpServletRequest request, @RequestBody ResourceInfo resourceInfo, @PathVariable("id") int id) throws PermissionDeniedException {
        String token = request.getHeader("Authorization");
        String phone = JWTUtil.getPayload(token).getAccount();
        if (!resourceService.hasPermission(phone, id)) {
            throw new PermissionDeniedException();
        }
        resourceInfoService.updateResourceInfoByResourceId(resourceInfo, id);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }
}
