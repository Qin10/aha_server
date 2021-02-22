package cn.hdustea.aha_server.constants;

/**
 * Redis相关常量类
 *
 * @author STEA_YY
 **/
public class RedisConstants {
    /**
     * 注册短信验证码Redis前缀
     */
    public static final String REGISTER_MESSAGE_CODE_PREFIX = "user:register:code:";

    /**
     * 修改密码短信验证码Redis前缀
     */
    public static final String CHANGE_PASSWORD_MESSAGE_CODE_PREFIX = "user:changePassword:code:";

    /**
     * 绑定手机号短信验证码Redis前缀
     */
    public static final String BIND_PHONE_MESSAGE_CODE_PREFIX = "user:bindPhone:code:";

    /**
     * RefreshToken Redis前缀
     */
    public static final String REFRESH_TOKEN_PREFIX = "user:token:";

    /**
     * 用户阅读项目标记 Redis前缀
     */
    public static final String USER_PROJECT_READ_PREFIX = "user:project:read:";

    /**
     * 请求限制 Redis前缀
     */
    public static final String REQUEST_LIMIT_PREFIX = "request:limit:";

    public static final String ACTIVITY_CODE_COUNT_PREFIX = "activity:code:count:";

    public static final String ACTIVITY_CODE_PREFIX = "activity:code:";

    public static final String PROJECT_UPLOAD_TEMP_TOKEN_PREFIX = "project:upload:token:";

    /**
     * 项目阅读量Hash key
     */
    public static final String PROJECT_READ_KEY = "project:read";
    /**
     * 用户排行榜Hash key
     */
    public static final String CONTRIBUTION_RANK_KEY = "contribution:rank";

    /**
     * 文档转换队列 key
     */
    public static final String DOCUMENT_CONVERT_LIST_KEY = "document:convert:list";

    /**
     * OSS文档转换任务id key
     */
    public static final String DOCUMENT_CONVERT_RUNNING_TASK_KEY = "document:convert:running:task";
}
