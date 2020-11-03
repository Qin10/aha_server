package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.OssPolicyBean;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.entity.Resource;
import cn.hdustea.aha_server.entity.ResourceInfo;
import cn.hdustea.aha_server.entity.ResourceMember;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.DeleteException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.exception.apiException.daoException.UpdateException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.service.ResourceInfoService;
import cn.hdustea.aha_server.service.ResourceService;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.RedisUtil;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j(topic = "userOperationLog")
@RestController
@RequestMapping("/resource")
public class ResourceController {
    private static final String MODULE_NAME = "注册登录模块";
    @javax.annotation.Resource
    private ResourceService resourceService;
    @javax.annotation.Resource
    private ResourceInfoService resourceInfoService;
    @javax.annotation.Resource
    private OssService ossService;
    @javax.annotation.Resource
    private RedisUtil redisUtil;
    @javax.annotation.Resource
    private UserOperationLogConfig userOperationLogConfig;

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
    @GetMapping("/sign/upload/private")
    public ResponseBean signUploadPrivateFile() {
        String phone = ThreadLocalUtil.getCurrentUser();
        OssPolicyBean ossPolicyBean = ossService.signUpload(phone, true);
        return new ResponseBean(200, "succ", ossPolicyBean, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin(requireSignContract = true)
    @GetMapping("/sign/upload/public")
    public ResponseBean signUploadPublicFile() {
        String phone = ThreadLocalUtil.getCurrentUser();
        OssPolicyBean ossPolicyBean = ossService.signUpload("resource/" + phone, false);
        return new ResponseBean(200, "succ", ossPolicyBean, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 创建新资源的接口
     *
     * @param resource 资源的实体类
     */
    @RequiresLogin(requireSignContract = true)
    @PostMapping()
    public ResponseBean saveResource(@RequestBody @Validated Resource resource) {
        String phone = ThreadLocalUtil.getCurrentUser();
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
    public ResponseBean updateResourceById(@RequestBody Resource resource, @PathVariable("id") int id) throws UpdateException, PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
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
    public ResponseBean deleteResourceById(@PathVariable("id") int id) throws PermissionDeniedException, DeleteException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!resourceService.hasPermission(phone, id)) {
            throw new PermissionDeniedException();
        }
        resourceService.deleteResourceById(id);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @GetMapping("/sign/download/{id}")
    public ResponseBean signDownloadResourceByid(@PathVariable("id") int id) throws SelectException {
        String url = resourceService.signDownloadResourceByid(id);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("url", url);
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "下载资源", "id=" + id);
        return new ResponseBean(200, "succ", responseMap, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @GetMapping("/info/{id}")
    public ResponseBean getResourceInfoById(@PathVariable("id") int id) {
        ResourceInfo resourceInfo = resourceInfoService.getResourceInfoByResourceId(id);
        if (resourceInfo != null) {
            addReadByResourceId(id);
        }
        log.info(userOperationLogConfig.getFormat(), MODULE_NAME, "查看资源", "id=" + id);
        return new ResponseBean(200, "succ", resourceInfo, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @PutMapping("/info/{id}")
    public ResponseBean updateResourceInfoById(@RequestBody ResourceInfo resourceInfo, @PathVariable("id") int id) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!resourceService.hasPermission(phone, id)) {
            throw new PermissionDeniedException();
        }
        resourceInfoService.updateResourceInfoByResourceId(resourceInfo, id);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin(requireSignContract = true)
    @PostMapping("/info/member/{id}")
    public ResponseBean saveResourceMemberById(@RequestBody ResourceMember resourceMember, @PathVariable("id") int id) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!resourceService.hasPermission(phone, id)) {
            throw new PermissionDeniedException();
        }
        resourceInfoService.saveResourceMemberByResId(resourceMember, id);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin(requireSignContract = true)
    @PutMapping("/info/member/{id}")
    public ResponseBean updateResourceMemberById(@RequestBody ResourceMember resourceMember, @PathVariable("id") int id) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!resourceService.hasPermission(phone, id)) {
            throw new PermissionDeniedException();
        }
        resourceInfoService.updateMemberJob(resourceMember.getJob(), id, resourceMember.getMemberPhone());
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    @RequiresLogin
    @DeleteMapping("/info/member/{id}/{phone}")
    public ResponseBean deleteResourceMember(@PathVariable("id") int id, @PathVariable("phone") String deletedPhone) throws PermissionDeniedException {
        String phone = ThreadLocalUtil.getCurrentUser();
        if (!resourceService.hasPermission(phone, id)) {
            throw new PermissionDeniedException();
        }
        resourceInfoService.deleteResourceMember(id, phone);
        return new ResponseBean(200, "succ", null, TimeUtil.getFormattedTime(new Date()));
    }

    private void addReadByResourceId(int resourceId) {
        redisUtil.hincr(RedisUtil.RESOURCE_READ_KEY, Integer.toString(resourceId), 1);
    }
}
