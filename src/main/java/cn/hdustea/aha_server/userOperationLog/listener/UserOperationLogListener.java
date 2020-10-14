package cn.hdustea.aha_server.userOperationLog.listener;

import cn.hdustea.aha_server.userOperationLog.dao.UserOperationLogDao;
import cn.hdustea.aha_server.userOperationLog.entity.UserOperationLog;
import cn.hdustea.aha_server.userOperationLog.event.UserOperationLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户操作日志异步监听器类
 *
 * @author STEA_YY
 */
@Slf4j
@Component
public class UserOperationLogListener {
    @Resource
    private UserOperationLogDao userOperationLogDao;

    @Async
    @Order
    @EventListener(UserOperationLogEvent.class)
    public void saveLog(UserOperationLogEvent event) {
        UserOperationLog log = (UserOperationLog) event.getSource();
        userOperationLogDao.saveUserOperationLog(log);
    }
}
