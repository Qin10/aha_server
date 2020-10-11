package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.exception.SmsException;
import cn.hdustea.aha_server.service.SmsService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @GetMapping("/sendCode/{phone}")
    public ResponseBean sendSmsCode(@PathVariable("phone") String phone) throws SmsException {
        boolean isSent = smsService.sendSmsCode(phone);
        if (isSent) {
            return new ResponseBean(200, "验证码发送成功！", null, TimeUtil.getFormattedTime(new Date()));
        } else {
            throw new SmsException("验证码发送失败！");
        }
    }
}
