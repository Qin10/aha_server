package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String REGISTER_MESSAGE_CODE_PREFIX = "user:register:code:";
    private static final String CHANGE_PASSWORD_MESSAGE_CODE_PREFIX = "user:changePassword:code:";
    private static final int MESSAGE_EXPIRED_TIME = 60 * 5;
    public static final int REGISTER_MESSAGE = 0;
    public static final int CHANGE_PASSWORD_MESSAGE = 1;

    /**
     * 向目标手机号发送验证码短信
     *
     * @param phone 手机号
     * @return code是否发送成功
     */
    public boolean sendSmsCode(String phone, int type) {
//        String code = makeSmsCode(4);
        String code = "1234";
        switch (type) {
            case REGISTER_MESSAGE: {
                System.out.println(2);
                redisUtil.set(REGISTER_MESSAGE_CODE_PREFIX + phone, code, MESSAGE_EXPIRED_TIME);
                break;
            }
            case CHANGE_PASSWORD_MESSAGE: {
                System.out.println(1);
                redisUtil.set(CHANGE_PASSWORD_MESSAGE_CODE_PREFIX + phone, code, MESSAGE_EXPIRED_TIME);
                break;
            }
        }
        return true;
    }

    /**
     * 生成随机验证码
     *
     * @param length 随机验证码的长度
     * @return
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
            case REGISTER_MESSAGE: {
                possibleCode = (String) redisUtil.get(REGISTER_MESSAGE_CODE_PREFIX + phone);
                break;
            }
            case CHANGE_PASSWORD_MESSAGE: {
                possibleCode = (String) redisUtil.get(CHANGE_PASSWORD_MESSAGE_CODE_PREFIX + phone);
                System.out.println(possibleCode);
                break;
            }
        }
        if (possibleCode == null) {
            return false;
        }
        System.out.println(possibleCode+code);
        return possibleCode.equals(code);
    }
}
