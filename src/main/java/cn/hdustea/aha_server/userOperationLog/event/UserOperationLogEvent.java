package cn.hdustea.aha_server.userOperationLog.event;

import cn.hdustea.aha_server.entity.UserOperationLog;
import org.springframework.context.ApplicationEvent;

/**
 * 用户操作日志事件
 *
 * @author STEA_YY
 */
public class UserOperationLogEvent extends ApplicationEvent {
    public UserOperationLogEvent(UserOperationLog source) {
        super(source);
    }
}
