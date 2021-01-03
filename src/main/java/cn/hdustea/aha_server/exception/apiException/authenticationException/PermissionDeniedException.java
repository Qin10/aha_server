package cn.hdustea.aha_server.exception.apiException.authenticationException;

import cn.hdustea.aha_server.constants.ApiExceptionCodes;
import cn.hdustea.aha_server.exception.apiException.AuthenticationException;

/**
 * 无权限异常
 *
 * @author STEA_YY
 **/
public class PermissionDeniedException extends AuthenticationException {
    public PermissionDeniedException() {
        super(ApiExceptionCodes.PERMISSION_DENIED.getDesc(), ApiExceptionCodes.PERMISSION_DENIED.getValue());
    }

    public PermissionDeniedException(String message) {
        super(message, ApiExceptionCodes.PERMISSION_DENIED.getValue());
    }
}
