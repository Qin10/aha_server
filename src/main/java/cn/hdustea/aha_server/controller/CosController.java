package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.config.TencentCosConfig;
import cn.hdustea.aha_server.service.CosService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.CosPostPolicyVo;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * COS相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/cos")
public class CosController {
    @Resource
    private CosService cosService;
    @Resource
    private TencentCosConfig tencentCosConfig;

    /**
     * 获取COS公开资源上传签名
     *
     * @param filename 待上传文件名
     */
    @RequiresLogin
    @GetMapping("/sign/upload/public")
    public ResponseBean<CosPostPolicyVo> signPublicUploadToCos(@RequestParam("filename") String filename) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        CosPostPolicyVo cosPostPolicyVo = cosService.signPostAuthorization("/user_files/" + userId + "/" + filename, tencentCosConfig.getPublicBucketName());
        return new ResponseBean<>(200, "succ", cosPostPolicyVo);
    }

    @RequiresLogin
    @GetMapping("/sign/upload/test")
    public ResponseBean<CosPostPolicyVo> signPostUploadToCos(@RequestParam("filename") String filename) {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        CosPostPolicyVo cosPostPolicyVo = cosService.signPostAuthorization("/user_files/" + userId + "/" + filename, tencentCosConfig.getPublicBucketName());
        return new ResponseBean<>(200, "succ", cosPostPolicyVo);
    }
}
