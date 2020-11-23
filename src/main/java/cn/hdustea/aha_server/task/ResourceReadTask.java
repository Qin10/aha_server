package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.entity.Project;
import cn.hdustea.aha_server.mapper.ProjectMapper;
import cn.hdustea.aha_server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private ProjectMapper projectMapper;

    @Scheduled(cron = "0 0 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateResourceRead() {
        log.debug("Project Read Task is Running");
        Map<Object, Object> projectReadMap = redisUtil.hmget(RedisUtil.PROJECT_READ_KEY);
        for (Map.Entry<Object, Object> entry : projectReadMap.entrySet()) {
            Integer projectId = Integer.parseInt((String) entry.getKey());
            Integer read = (Integer) entry.getValue();
            if (read != null && read > 0) {
                Project project = projectMapper.selectByPrimaryKey(projectId);
                if (project != null) {
                    int updatedRead = project.getRead() + read;
                    projectMapper.updateReadById(updatedRead, projectId);
                }
                redisUtil.hdel(RedisUtil.PROJECT_READ_KEY, Integer.toString(projectId));
            }
        }
    }
}