package cn.hdustea.aha_server.vo;

import cn.hdustea.aha_server.util.TimeUtil;
import lombok.Data;

import java.util.Date;

/**
 * 响应数据封装vo
 *
 * @author STEA_YY
 **/
@Data
public class ResponseBean<T> {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 响应时间
     */
    private String time;

    public ResponseBean(int code, String msg, T data, String time) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = time;
    }

    public ResponseBean(int code, String msg, T data) {
        this(code, msg, data, TimeUtil.getFormattedTime(new Date()));
    }
}
