package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.service.WechatProgramService;
import cn.hdustea.aha_server.vo.TokenVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 微信小程序授权/鉴权相关请求
 *
 * @author STEA_YY
 **/
@RestController
public class WechatProgramController {
    @Resource
    private WechatProgramService wechatProgramService;

    /**
     * 通过微信小程序授权登录
     *
     * @param code 小程序请求码
     * @throws Exception 向上抛出异常
     */
    @PostMapping("/wxLogin")
    public ResponseBean<TokenVo> wechatLogin(@RequestParam("code") String code) throws Exception {
        String token = wechatProgramService.wechatLogin(code);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(token);
        return new ResponseBean<>(200, "登录成功", tokenVo);
    }
}
