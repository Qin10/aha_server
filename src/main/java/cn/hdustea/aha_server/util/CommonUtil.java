package cn.hdustea.aha_server.util;

import java.util.Random;

/**
 * 通用工具类
 *
 * @author STEA_YY
 **/
public class CommonUtil {
    private static final String BASE_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 生成随机字符串
     *
     * @param length 随机数长度
     * @return 随机值
     */
    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE_CHARS.length());
            stringBuffer.append(BASE_CHARS.charAt(number));
        }
        return stringBuffer.toString();
    }
}
