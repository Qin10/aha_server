package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsService {
    @Autowired
    private RedisUtil redisUtil;
    private static final String REGISTER_MESSAGE_CODE_PREFIX = "user:register:code:";
    private static final int MESSAGE_EXPIRED_TIME = 60 * 5;

    public boolean sendSmsCode(String phone) {
//        String code = makeSmsCode(4);
        String code = "1234";
        redisUtil.set(REGISTER_MESSAGE_CODE_PREFIX + phone, code, MESSAGE_EXPIRED_TIME);
        return true;
    }

    public String makeSmsCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int next = random.nextInt(10);
            code.append(next);
        }
        return code.toString();
    }

    public boolean verifySmsCode(String phone, String code) {
        String possibleCode = (String) redisUtil.get(REGISTER_MESSAGE_CODE_PREFIX + phone);
        if (possibleCode == null) {
            return false;
        }
        return possibleCode.equals(code);
    }
}
