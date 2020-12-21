package cn.hdustea.aha_server.task;

import cn.hdustea.aha_server.dto.ResourceAvgScoreDto;
import cn.hdustea.aha_server.mapper.ProjectResourceMapper;
import cn.hdustea.aha_server.mapper.ProjectResourceScoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源评分任务类
 *
 * @author STEA_YY
 **/
@Component
@Slf4j
public class ResourceScoreTask {
    @Resource
    private ProjectResourceScoreMapper projectResourceScoreMapper;
    @Resource
    private ProjectResourceMapper projectResourceMapper;

    /**
     * 批量更新项目资源平均分和评价人数
     */
    @Scheduled(cron = "0 0 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void updateResourceScore() {
        log.debug("Resource Score Task is Running");
        List<ResourceAvgScoreDto> resourceAvgScoreDtos = projectResourceScoreMapper.selectAllAvgScore();
        for (ResourceAvgScoreDto resourceAvgScoreDto : resourceAvgScoreDtos) {
            Integer count = projectResourceScoreMapper.countByResourceId(resourceAvgScoreDto.getResourceId());
            projectResourceMapper.updateScoreAndScoreCountById(resourceAvgScoreDto.getScore(), count, resourceAvgScoreDto.getResourceId());
        }
    }
}