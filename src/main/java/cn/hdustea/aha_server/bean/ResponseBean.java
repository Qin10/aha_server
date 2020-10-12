package cn.hdustea.aha_server.bean;

import lombok.Data;

/**
 * 包裹响应数据的Javabean
 *
 * @author STEA_YY
 **/
@Data
public class ResponseBean {
    // http 状态码
    private int code;
    // 返回信息
    private String msg;
    // 返回的数据
    private Object data;
    // 返回的时间字符串
    private String time;

    public ResponseBean(int code, String msg, Object data, String time) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = time;
    }
}
