package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.service.OssService;
import cn.hdustea.aha_server.vo.UrlVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URL;

/**
 * 文件下载相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private OssService ossService;

    /**
     * 获取oss签名，已弃用
     *
     * @param filename 文件名
     */
    @RequiresLogin
    @GetMapping("/{filename}")
    public ResponseBean<UrlVo> signDownload(@PathVariable("filename") String filename) {
        URL url = ossService.signDownload(filename);
        UrlVo urlVo = new UrlVo();
        urlVo.setUrl(url.toString());
        return new ResponseBean<>(200, "succ", urlVo);
    }

}
