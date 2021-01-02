package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.ContribPointLog;
import cn.hdustea.aha_server.mapper.ContribPointLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 贡献点变动日志服务类
 *
 * @author STEA_YY
 **/
@Service
public class ContribPointLogService {
    @Resource
    private ContribPointLogMapper contribPointLogMapper;

    public void saveContribPointLog(ContribPointLog contribPointLog) {
        contribPointLogMapper.insertSelective(contribPointLog);
    }

    public List<ContribPointLog> getAllContribPointLogByUserId(int userId) {
        return contribPointLogMapper.selectAllByUserIdOrderByTimeDesc(userId);
    }
}
