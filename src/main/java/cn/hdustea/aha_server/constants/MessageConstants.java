package cn.hdustea.aha_server.constants;

/**
 * 站内信常量类
 *
 * @author STEA_YY
 **/
public class MessageConstants {
    /**
     * 站内信未读
     */
    public static final int STATUS_NOT_READ = 0;

    /**
     * 站内信已读
     */
    public static final int STATUS_READ = 1;

    /**
     * 站内信被删除
     */
    public static final int STATUS_DELETED = 2;

    /**
     * 系统消息
     */
    public static final int TYPE_SYSTEM = 0;

    /**
     * 私信
     */
    public static final int TYPE_PRIVATE = 1;

    /**
     * 站内通知
     */
    public static final int TYPE_NOTICE = 2;

    /**
     * 站内系统通知
     */
    public static final int TYPE_SYSTEM_NOTICE = 3;
}
