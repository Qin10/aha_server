package cn.hdustea.aha_server.aspect;

import cn.hdustea.aha_server.annotation.RequestLimit;
import cn.hdustea.aha_server.util.RedisUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 访问次数限制切面类
 *
 * @author STEA_YY
 **/
@Aspect
@Component
public class RequestLimitAspect {
    @Resource
    private RedisUtil redisUtil;

    @Pointcut("@annotation(requestLimit)")
    public void pointCut(RequestLimit requestLimit) {
    }

//    @Before(value = "pointCut(requestLimit)", argNames = "joinpoint,requestLimit")
//    public void before(JoinPoint joinpoint, RequestLimit requestLimit) throws RequestTimesExceededException {
//        ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attribute != null) {
//            HttpServletRequest request = attribute.getRequest();
//            String ip = IpUtil.getIpAddr(request);
//            String className = joinpoint.getTarget().getClass().getName();
//            String methodName = joinpoint.getSignature().getName();
//            String key = RedisUtil.REQUEST_LIMIT_PREFIX + ip + ":" + className + "." + methodName;
//            Integer currentAmount = (Integer) redisUtil.get(key);
//            if (currentAmount == null) {
//                redisUtil.set(key, 1, requestLimit.time());
//            } else {
//                if (currentAmount > requestLimit.amount()) {
//                    throw new RequestTimesExceededException();
//                } else {
//                    redisUtil.incr(key, 1);
//                }
//            }
//        }
//    }
}
