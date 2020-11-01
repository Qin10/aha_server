package cn.hdustea.aha_server.util;

import cn.hdustea.aha_server.bean.JwtPayloadBean;

/**
 * 线程隔离变量相关工具类
 *
 * @author STEA_YY
 **/
public class ThreadLocalUtil {
    private static final ThreadLocal<String> CURRENT_USER_THREAD_LOCAL = new ThreadLocal<String>();

    public static void setCurrentUser(String phone) {
        CURRENT_USER_THREAD_LOCAL.set(phone);
    }

    public static String getCurrentUser() {
        return CURRENT_USER_THREAD_LOCAL.get();
    }

    public static void removeCurrentUser() {
        CURRENT_USER_THREAD_LOCAL.remove();
    }
}
