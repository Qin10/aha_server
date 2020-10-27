package cn.hdustea.aha_server.userOperationLog.aspect;

import cn.hdustea.aha_server.entity.UserOperationLog;
import cn.hdustea.aha_server.userOperationLog.annotation.LogUserOperation;
import cn.hdustea.aha_server.userOperationLog.event.UserOperationLogEvent;
import cn.hdustea.aha_server.util.IpUtil;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * 用户操作日志切面类
 *
 * @author STEA_YY
 **/
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Resource
    private ApplicationContext applicationContext;
    private UserOperationLog userOperationLog;
    private long beginTime;

    /**
     * 声明切入点
     */
    @Pointcut("@annotation(cn.hdustea.aha_server.userOperationLog.annotation.LogUserOperation)")
    public void pointcut() {
    }

    /**
     * 在方法执行之前获取请求参数，并创建log对象，写入方法参数
     *
     * @param joinPoint 切入点
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        userOperationLog = new UserOperationLog();
        userOperationLog.setCreateTime(new Date());
        beginTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 设置IP地址
        userOperationLog.setIp(IpUtil.getIpAddr(request));
        // 从token中获取用户名
        String token = request.getHeader("Authorization");
        if (token != null) {
            String account = JWTUtil.getPayload(token).getAccount();
            userOperationLog.setAccount(account);
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        userOperationLog.setMethod(className + "." + methodName + "()");
        LogUserOperation logAnnotation = method.getAnnotation(LogUserOperation.class);
        if (logAnnotation != null) {
            // 注解上的描述
            userOperationLog.setOperation(logAnnotation.value());
        }
        // 请求的方法名
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        Object[] args = joinPoint.getArgs();
        HashMap<String, Object> argsMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            argsMap.put(paramNames[i], args[i]);
        }
        String params = null;
        try {
            params = JacksonUtil.obj2json(argsMap);
        } catch (Exception e) {
            log.error("Parse Json failed", e);
        }
        userOperationLog.setParams(params);
    }

    /**
     * 在方法执行之后写入方法执行耗时并提交插入log对象至数据库
     *
     * @param ret 方法返回值
     */
    @AfterReturning(pointcut = "pointcut()", returning = "ret")
    public void afterReturning(Object ret) {
        long time = System.currentTimeMillis() - beginTime;
        userOperationLog.setTime((Long) time);
        applicationContext.publishEvent(new UserOperationLogEvent(userOperationLog));
    }

}
