package cn.hdustea.aha_server.constants;

/**
 * 正则表达式常量类
 *
 * @author STEA_YY
 */
public class RegexConstants {
    //------------------常量定义
    /**
     * Email正则表达式
     */
    public static final String EMAIL = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
    /**
     * 电话号码正则表达式
     */
    public static final String TELE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 手机号码正则表达式
     */
    public static final String MOBILE = "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";
}