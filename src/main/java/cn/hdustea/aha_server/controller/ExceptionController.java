package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.enums.ApiExceptionCode;
import cn.hdustea.aha_server.exception.ApiException;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;
import cn.hdustea.aha_server.exception.apiException.ForbiddenException;
import cn.hdustea.aha_server.util.TimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * 统一异常处理控制器
 *
 * @author STEA_YY
 **/
@RestControllerAdvice
public class ExceptionController {

    /**
     * 捕捉用户鉴权相关异常
     *
     * @param e 鉴权失败异常类
     * @return 返回错误码和HTTP401
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseBean handleShiroException(AuthenticationException e) {
//        e.printStackTrace();
        return new ResponseBean(e.getCode(), e.getMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 捕捉禁止访问异常
     *
     * @param e 禁止访问异常类
     * @return 返回错误码和HTTP403
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseBean handleForbiddenException(ForbiddenException e) {
        return new ResponseBean(e.getCode(), e.getMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 捕获其他API异常
     *
     * @param e Api异常类
     * @return 返回错误码和HTTP500
     */
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean handleApiException(ApiException e) {
        return new ResponseBean(e.getCode(), e.getMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 捕捉参数校验异常
     *
     * @param e 参数校验异常类
     * @return 返回错误码和HTTP500
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseBean(ApiExceptionCode.ARGUMENTS_VALID_FAIL.getValue(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 捕捉其他所有异常
     *
     * @param e 异常类
     * @return 返回500错误码和HTTP500
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean globalException(Exception e) {
        e.printStackTrace();
        return new ResponseBean(500, e.getMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

}
