package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.constants.OauthTypes;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.constants.SmsConstants;
import cn.hdustea.aha_server.entity.Oauth;
import cn.hdustea.aha_server.exception.apiException.smsException.MessageSendException;
import cn.hdustea.aha_server.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

/**
 * 短信服务类
 *
 * @author STEA_YY
 **/
@Service
public class SmsService {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private OauthService oauthService;
    private static final int MESSAGE_EXPIRED_TIME = 60 * 5;

    /**
     * 向目标手机号发送验证码短信
     *
     * @param phone 手机号
     * @return code是否发送成功
     */
    public boolean sendSmsCode(String phone, String type) throws MessageSendException {
//        String code = makeSmsCode(4);
        Oauth oauth = oauthService.getOauthByOauthTypeAndOauthId(OauthTypes.PHONE, phone);
        String code = "1234";
        switch (type) {
            case "register": {
                if (oauth != null) {
                    throw new MessageSendException("该手机号已被注册！");
                }
                redisUtil.set(RedisConstants.REGISTER_MESSAGE_CODE_PREFIX + phone, code, MESSAGE_EXPIRED_TIME);
                break;
            }
            case "changePassword": {
                if (oauth == null) {
                    throw new MessageSendException("用户不存在！");
                }
                redisUtil.set(RedisConstants.CHANGE_PASSWORD_MESSAGE_CODE_PREFIX + phone, code, MESSAGE_EXPIRED_TIME);
                break;
            }
            case "bindPhone": {
                if (oauth != null) {
                    throw new MessageSendException("该账号已被绑定！");
                }
                redisUtil.set(RedisConstants.BIND_PHONE_MESSAGE_CODE_PREFIX + phone, code, MESSAGE_EXPIRED_TIME);
                break;
            }
            default: {
                throw new MessageSendException("'type'字段取值错误！");
            }
        }
        return true;
    }

    /**
     * 生成随机验证码
     *
     * @param length 随机验证码的长度
     * @return 随机验证码
     */
    public String makeSmsCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int next = random.nextInt(10);
            code.append(next);
        }
        return code.toString();
    }

    /**
     * 校验验证码是否正确
     *
     * @param phone 手机号
     * @param code  传入验证码
     * @return 校验结果
     */
    public boolean verifySmsCode(String phone, String code, int type) {
        String possibleCode = null;
        switch (type) {
            case SmsConstants.REGISTER_MESSAGE: {
                possibleCode = (String) redisUtil.get(RedisConstants.REGISTER_MESSAGE_CODE_PREFIX + phone);
                break;
            }
            case SmsConstants.CHANGE_PASSWORD_MESSAGE: {
                possibleCode = (String) redisUtil.get(RedisConstants.CHANGE_PASSWORD_MESSAGE_CODE_PREFIX + phone);
                break;
            }
            case SmsConstants.BIND_PHONE_MESSAGE: {
                possibleCode = (String) redisUtil.get(RedisConstants.BIND_PHONE_MESSAGE_CODE_PREFIX + phone);
                break;
            }
        }
        if (possibleCode == null) {
            return false;
        }
        return possibleCode.equals(code);
    }
}
