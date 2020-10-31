package cn.hdustea.aha_server.userOperationLog.listener;


import cn.hdustea.aha_server.mapper.UserOperationLogMapper;
import cn.hdustea.aha_server.entity.UserOperationLog;
import cn.hdustea.aha_server.userOperationLog.event.UserOperationLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户操作日志异步监听器类
 *
 * @author STEA_YY
 */
@EnableAsync
@Slf4j
@Component
public class UserOperationLogListener {
    @Resource
    private UserOperationLogMapper userOperationLogMapper;

    @Async
    @Order
    @EventListener(UserOperationLogEvent.class)
    public void saveLog(UserOperationLogEvent event) {
        UserOperationLog log = (UserOperationLog) event.getSource();
        userOperationLogMapper.insertSelective(log);
    }
}
