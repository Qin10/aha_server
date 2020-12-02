package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.dto.SmsSendDto;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageSendException;
import cn.hdustea.aha_server.service.SmsService;
import cn.hdustea.aha_server.vo.ResponseBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 短信业务相关请求
 *
 * @author STEA_YY
 **/
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Resource
    private SmsService smsService;

    /**
     * 发送短信验证码
     *
     * @param smsSendDto 短信发送相关信息
     */
    @RequestLimit(amount = 5, time = 300)
    @PostMapping("/code/{phone}")
    public ResponseBean<Object> sendRegisterSmsCode(@Validated @RequestBody SmsSendDto smsSendDto) throws MessageSendException {
        boolean isSent = smsService.sendSmsCode(smsSendDto.getPhone(), smsSendDto.getType());
        if (isSent) {
            return new ResponseBean<>(200, "验证码发送成功！", null);
        } else {
            throw new MessageSendException();
        }
    }
}
