package cn.hdustea.aha_server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author STEA_YY
 */
public class TimeUtil {
    /**
     * 传入日期类生成格式化的时间字符串
     *
     * @param date 日期类
     * @return 格式化的时间字符串
     */
    public static String getFormattedTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 传入日期类生成格式化的日期字符串
     *
     * @param date 日期类
     * @return 格式化的日期字符串
     */
    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 传入日期字符串生成日期类
     *
     * @param dateStr 日期字符串
     * @return 日期类
     * @throws ParseException
     */
    public static Date tranStringToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateStr);
    }
}
