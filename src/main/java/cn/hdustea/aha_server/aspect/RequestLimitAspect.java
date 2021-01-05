package cn.hdustea.aha_server.aspect;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.constants.RedisConstants;
import cn.hdustea.aha_server.exception.runtimeApiException.RequestTimesExceededException;
import cn.hdustea.aha_server.service.RedisService;
import cn.hdustea.aha_server.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 访问次数限制切面类
 *
 * @author STEA_YY
 **/
@Aspect
@Component
public class RequestLimitAspect {
    @Resource
    private RedisService redisService;

    @Pointcut("@annotation(requestLimit)")
    public void pointCut(RequestLimit requestLimit) {
    }

    @Before(value = "pointCut(requestLimit)", argNames = "joinpoint,requestLimit")
    public void before(JoinPoint joinpoint, RequestLimit requestLimit) throws RequestTimesExceededException {
        ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attribute != null) {
            HttpServletRequest request = attribute.getRequest();
            String ip = IpUtil.getIpAddr(request);
            String className = joinpoint.getTarget().getClass().getName();
            String methodName = joinpoint.getSignature().getName();
            String key = RedisConstants.REQUEST_LIMIT_PREFIX + ip + ":" + className + "." + methodName;
            Integer currentAmount = (Integer) redisService.get(key);
            if (currentAmount == null) {
                redisService.set(key, 1, requestLimit.time());
            } else {
                if (currentAmount > requestLimit.amount()) {
                    throw new RequestTimesExceededException();
                } else {
                    redisService.incr(key, 1);
                }
            }
        }
    }
}
