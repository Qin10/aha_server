package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * 回调函数主体Javabean
 *
 * @author STEA_YY
 **/
@Data
public class CallbackBodyBean {
    private String object;
    private String mimeType;
    private long size;
    private String account;
}
