package cn.hdustea.aha_server.service;

import cn.hdustea.aha_server.entity.ContribPointLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 贡献点(发放)服务类
 *
 * @author STEA_YY
 **/
@Service
public class ContribPointService {
    @Resource
    private ContribPointLogService contribPointLogService;
    @Resource
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public void sendContribPoint(int targetUserId, int type, Integer externalId, BigDecimal ahaPointAmount,BigDecimal ahaCreditAmount) {
        ContribPointLog contribPointLog = new ContribPointLog();
        contribPointLog.setUserId(targetUserId);
        contribPointLog.setType(type);
        contribPointLog.setExternalId(externalId);
        contribPointLog.setAhaPointAmount(ahaPointAmount);
        contribPointLog.setAhaCreditAmount(ahaCreditAmount);
        contribPointLog.setTime(new Date());
        contribPointLogService.saveContribPointLog(contribPointLog);
        if (ahaCreditAmount!=null){
            userService.updateIncAhaCredit(targetUserId,ahaCreditAmount);
        }
        if (ahaPointAmount!=null){
            userService.updateIncAhaPoint(targetUserId,ahaPointAmount);
        }
    }
}
