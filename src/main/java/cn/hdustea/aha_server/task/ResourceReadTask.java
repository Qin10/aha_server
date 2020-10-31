package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.dao.ResourceMapper;
import cn.hdustea.aha_server.entity.Resource;
import cn.hdustea.aha_server.service.ResourceService;
import cn.hdustea.aha_server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        for (int id : resourceIds) {
            Integer read = (Integer) redisUtil.get(RedisUtil.RESOURCE_READ_PREFIX + id);
            if (read != null && read > 0) {
                Resource resource = resourceMapper.selectByPrimaryKey(id);
                int updatedRead = resource.getRead() + read;
                resourceMapper.updateReadById(updatedRead, id);
                redisUtil.del(RedisUtil.RESOURCE_READ_PREFIX + id);
            }
        }
    }
}