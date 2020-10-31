package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.mapper.ResourceMapper;
import cn.hdustea.aha_server.entity.Resource;
import cn.hdustea.aha_server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 资源浏览量每日定时入库的任务类
 *
 * @author STEA_YY
 **/
@Component
@Slf4j
public class ResourceReadTask {
    @javax.annotation.Resource
    private RedisUtil redisUtil;
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @Scheduled(cron = "0 0 6,23 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateResourceRead() {
        List<Integer> resourceIds = resourceMapper.selectId();
        log.info("Resource Read Task is Running");
        Map<Object, Object> resourceReadMap = redisUtil.hmget(RedisUtil.RESOURCE_READ_KEY);
        for (Map.Entry<Object, Object> entry : resourceReadMap.entrySet()) {
            Integer resourceId = Integer.parseInt((String) entry.getKey());
            Integer read = (Integer) entry.getValue();
            if (read != null && read > 0) {
                Resource resource = resourceMapper.selectByPrimaryKey(resourceId);
                if (resource != null) {
                    int updatedRead = resource.getRead() + read;
                    resourceMapper.updateReadById(updatedRead, resourceId);
                }
                redisUtil.hdel(RedisUtil.RESOURCE_READ_KEY, Integer.toString(resourceId));
            }
        }
    }
}