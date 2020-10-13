package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.exception.apiException.SmsException;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageSendException;
import cn.hdustea.aha_server.service.SmsService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 短信业务控制器
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Resource
    private SmsService smsService;

    /**
     * 向目标手机号发送短信验证码的接口
     *
     * @param phone 手机号
     * @return
     * @throws MessageSendException 验证短信发送失败异常类
     */
    @GetMapping("/sendCode/{phone}")
    public ResponseBean sendSmsCode(@PathVariable("phone") String phone) throws MessageSendException {
        boolean isSent = smsService.sendSmsCode(phone);
        if (isSent) {
            return new ResponseBean(200, "验证码发送成功！", null, TimeUtil.getFormattedTime(new Date()));
        } else {
            throw new MessageSendException();
        }
    }
}
