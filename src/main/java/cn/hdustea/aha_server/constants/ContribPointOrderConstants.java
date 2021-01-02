package cn.hdustea.aha_server.constants;

import java.math.BigDecimal;

/**
 * 贡献点订单常量类
 *
 * @author STEA_YY
 **/
public class ContribPointOrderConstants {
    public static final int STATUS_NOT_PAID = 0;
    public static final int STATUS_PAID = 1;
    public static final int STATUS_CANCELED = 2;
    public static final String ACTION_PAY = "pay";
    public static final String ACTION_CENCAL = "cancel";
    public static final BigDecimal PERCENTAGE_RATE = BigDecimal.valueOf(0.4);
}
