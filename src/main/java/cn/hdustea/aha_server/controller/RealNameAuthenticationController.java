package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.constants.UserConstants;
import cn.hdustea.aha_server.dto.RealNameAuthenticationDto;
import cn.hdustea.aha_server.exception.apiException.authenticationException.PermissionDeniedException;
import cn.hdustea.aha_server.exception.apiException.daoException.SelectException;
import cn.hdustea.aha_server.service.RealNameAuthenticationService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.CosPostPolicyVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 实名认证类请求
 *
 * @author STEA_YY
 **/
@RestController()
@RequestMapping("/authentication")
public class RealNameAuthenticationController {
    @Resource
    private RealNameAuthenticationService realNameAuthenticationService;

    /**
     * 获取实名认证文件COS上传签名
     *
     * @param filename 文件名
     */
    @GetMapping("/sign/upload")
    @RequiresLogin
    public ResponseBean<CosPostPolicyVo> signProfileUploadToCos(@RequestParam("filename") String filename) throws PermissionDeniedException, SelectException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer status = realNameAuthenticationService.getAuthenticationStatusByUserId(userId);
        if (status.equals(UserConstants.AUTHENTICATION_STATUS_AUTHENTICATED)) {
            throw new PermissionDeniedException("您已经完成实名认证！");
        }
        CosPostPolicyVo cosPostPolicyVo = realNameAuthenticationService.signUpload(filename, userId);
        return new ResponseBean<>(200, "succ", cosPostPolicyVo);
    }

    /**
     * 更新实名认证信息
     *
     * @param realNameAuthenticationDto 实名认证信息
     */
    @PutMapping()
    @RequiresLogin
    public ResponseBean<Object> updatePersonalAuthentication(@RequestBody RealNameAuthenticationDto realNameAuthenticationDto) throws PermissionDeniedException {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        Integer status = realNameAuthenticationService.getAuthenticationStatusByUserId(userId);
        if (status.equals(UserConstants.AUTHENTICATION_STATUS_AUTHENTICATED)) {
            throw new PermissionDeniedException("您已经完成实名认证！");
        }
        realNameAuthenticationService.updateRealNameAuthenticationByUserId(realNameAuthenticationDto, userId);
        return new ResponseBean<>(200, "succ", null);
    }
}
