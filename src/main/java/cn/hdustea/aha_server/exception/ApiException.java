package cn.hdustea.aha_server.exception;

import lombok.Data;

/**
 * 服务器抛出（接口）异常类
 *
 * @author STEA_YY
 **/
@Data
public class ApiException extends Exception {
    private Integer code;

    public ApiException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ApiException() {
    }
}
