package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * OSS回调封装Javabean
 *
 * @author STEA_YY
 **/
@Data
public class CallbackBean {
    private String callbackUrl;
    private String callbackBody;
    private String callbackBodyType;
}
