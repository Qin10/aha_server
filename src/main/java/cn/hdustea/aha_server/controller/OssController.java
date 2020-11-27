package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.exception.apiException.ForbiddenException;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.util.JacksonUtil;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * OSS相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/oss")
public class OssController {
    @Resource
    private OssService ossService;

    /**
     * 内容安全扫描异常回调(OSS端发送)
     *
     * @param checksum 校验信息
     * @param content  返回实体(json)
     */
    @PostMapping("/green/callback")
    public ResponseBean<Object> freezeProjectResource(String checksum, String content) throws Exception {
        boolean verified = ossService.verifyOssGreenCallback(checksum, content);
        if (!verified) {
            throw new ForbiddenException("checksum校验失败！");
        }
        Map<String, Object> responseMap = JacksonUtil.json2map(content);
        ossService.freezeProjectResource(responseMap.get("object").toString(), (Boolean) responseMap.get("freezed"));
        return new ResponseBean<>(200, "succ", null);
    }
}