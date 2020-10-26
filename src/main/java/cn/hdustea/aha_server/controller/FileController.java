package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

/**
 * 文件下载相关的控制类
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private OssService ossService;

    @RequiresLogin
    @GetMapping("/{filename}")
    public ResponseBean signDownload(@PathVariable("filename") String filename) {
        URL url = ossService.signDownload(filename);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("url", url.toString());
        return new ResponseBean(200, "succ", responseMap, TimeUtil.getFormattedTime(new Date()));
    }
}
