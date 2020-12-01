package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequiresLogin;
import cn.hdustea.aha_server.config.UserOperationLogConfig;
import cn.hdustea.aha_server.service.WechatProgramService;
import cn.hdustea.aha_server.util.ThreadLocalUtil;
import cn.hdustea.aha_server.vo.ResponseBean;
import cn.hdustea.aha_server.vo.TokenAndPersonalUserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 微信小程序授权/鉴权相关请求
 *
 * @author STEA_YY
 **/
@Slf4j(topic = "userOperationLog")
@RestController
public class WechatProgramController {
    @Resource
    private WechatProgramService wechatProgramService;
    @Resource
    private UserOperationLogConfig userOperationLogConfig;

    /**
     * 通过微信小程序授权登录
     *
     * @param code 小程序请求码
     */
    @PostMapping("/wxLogin")
    public ResponseBean<TokenAndPersonalUserInfoVo> wechatLogin(@RequestParam("code") String code) throws Exception {
        TokenAndPersonalUserInfoVo tokenAndPersonalUserInfoVo = wechatProgramService.wechatLogin(code);
        log.info(userOperationLogConfig.getFormat(), AuthController.MODULE_NAME, "用户登录", "");
        return new ResponseBean<>(200, "登录成功", tokenAndPersonalUserInfoVo);
    }

    /**
     * 绑定微信账号
     *
     * @param code 小程序请求码
     */
    @RequiresLogin
    @PostMapping("/wxBind")
    public ResponseBean<Object> wechatBind(@RequestParam("code") String code) throws Exception {
        Integer userId = ThreadLocalUtil.getCurrentUser();
        wechatProgramService.wechatBind(userId, code);
        return new ResponseBean<>(200, "succ", null);
    }
}
