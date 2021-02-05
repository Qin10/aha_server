package cn.hdustea.aha_server.constants;

import java.math.BigDecimal;

/**
 * 项目资源常量类
 *
 * @author STEA_YY
 **/
public class ProjectResourceConstants {
    /**
     * 图片类资源
     */
    public static final int TYPE_PICTURE = 0;
    /**
     * 视频、音频类资源
     */
    public static final int TYPE_MEDIA = 1;
    /**
     * 文档类资源
     */
    public static final int TYPE_DOCUMENT = 2;
    /**
     * 其他资源
     */
    public static final int TYPE_OTHER = 3;

    public static final BigDecimal REWARD_COEFFICIENT = BigDecimal.valueOf(1.25);
}
