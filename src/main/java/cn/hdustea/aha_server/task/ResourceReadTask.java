package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.entity.Project;
import cn.hdustea.aha_server.mapper.ProjectMapper;
import cn.hdustea.aha_server.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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
    private RedisService redisService;
    @javax.annotation.Resource
    private ProjectMapper projectMapper;

    /**
     * 批量更新项目阅读量
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void updateResourceRead() {
        log.debug("Project Read Task is Running");
        Map<Object, Object> projectReadMap = redisService.hGetMap(RedisConstants.PROJECT_READ_KEY);
        for (Map.Entry<Object, Object> entry : projectReadMap.entrySet()) {
            Integer projectId = Integer.parseInt((String) entry.getKey());
            Integer read = (Integer) entry.getValue();
            if (read != null && read > 0) {
                Project project = projectMapper.selectByPrimaryKey(projectId);
                if (project != null) {
                    int updatedRead = project.getRead() + read;
                    projectMapper.updateReadById(updatedRead, projectId);
                }
                redisService.hDel(RedisConstants.PROJECT_READ_KEY, Integer.toString(projectId));
            }
        }
    }
}