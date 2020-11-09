package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.vo.ResponseBean;
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
     * 发送注册短信验证码
     *
     * @param phone 手机号
     * @throws MessageSendException 验证短信发送失败异常
     */
    @GetMapping("/sendCode/register/{phone}")
    public ResponseBean<Object> sendRegisterSmsCode(@PathVariable("phone") String phone) throws MessageSendException {
        boolean isSent = smsService.sendSmsCode(phone, SmsService.REGISTER_MESSAGE);
        if (isSent) {
            return new ResponseBean<>(200, "验证码发送成功！", null, TimeUtil.getFormattedTime(new Date()));
        } else {
            throw new MessageSendException();
        }
    }

    /**
     * 发送重置密码短信验证码
     *
     * @param phone 手机号
     * @throws MessageSendException 验证短信发送失败异常
     */
    @GetMapping("/sendCode/changePassword/{phone}")
    public ResponseBean<Object> sendSmsCode(@PathVariable("phone") String phone) throws MessageSendException {
        boolean isSent = smsService.sendSmsCode(phone, SmsService.CHANGE_PASSWORD_MESSAGE);
        if (isSent) {
            return new ResponseBean<>(200, "验证码发送成功！", null, TimeUtil.getFormattedTime(new Date()));
        } else {
            throw new MessageSendException();
        }
    }
}
