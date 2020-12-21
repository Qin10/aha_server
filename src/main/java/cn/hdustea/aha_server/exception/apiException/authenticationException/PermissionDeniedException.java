package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.constants.ApiExceptionCode;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 无权限异常
 *
 * @author STEA_YY
 **/
public class PermissionDeniedException extends AuthenticationException {
    public PermissionDeniedException() {
        super(ApiExceptionCode.PERMISSION_DENIED.getDesc(), ApiExceptionCode.PERMISSION_DENIED.getValue());
    }

    public PermissionDeniedException(String message) {
        super(message, ApiExceptionCode.PERMISSION_DENIED.getValue());
    }
}
