package cn.hdustea.aha_server.controller;

import cn.hdustea.aha_server.bean.ResponseBean;
import cn.hdustea.aha_server.exception.DaoException;
import cn.hdustea.aha_server.exception.ForbiddenException;
import cn.hdustea.aha_server.service.AuthService;
import cn.hdustea.aha_server.util.TimeUtil;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 统一异常处理控制器
 *
 * @author STEA_YY
 **/
@RestControllerAdvice
public class ExceptionController {

    @Resource
    private AuthService authService;

    /**
     * 捕捉shiro框架相关异常
     *
     * @param e
     * @return 响应403
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handleShiroException(ShiroException e) {
        e.printStackTrace();
        return new ResponseBean(403, e.getMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 捕捉禁止访问异常
     *
     * @param e
     * @return 响应403
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseBean handleForbiddenException(ForbiddenException e) {
        return new ResponseBean(403, e.getMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

    /**
     * 捕获数据库操作异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DaoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean handleDaoException(DaoException e) {
        return new ResponseBean(e.getErrorCode() != null ? e.getErrorCode() : 500, e.getMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        return new ResponseBean(getStatus(request).value(), ex.getMessage(), null, TimeUtil.getFormattedTime(new Date()));
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
