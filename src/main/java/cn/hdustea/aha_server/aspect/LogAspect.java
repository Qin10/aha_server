package cn.hdustea.aha_server.aspect;

import cn.hdustea.aha_server.dao.UserOperationLogDao;
import cn.hdustea.aha_server.entity.UserOperationLog;
import cn.hdustea.aha_server.util.IpUtil;
import cn.hdustea.aha_server.util.JWTUtil;
import cn.hdustea.aha_server.util.JacksonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

/**
 * 用户操作日志切面类
 *
 * @author STEA_YY
 **/
@Aspect
@Component
public class LogAspect {
    @Resource
    private UserOperationLogDao userOperationLogDao;

    /**
     * 声明切入点
     */
    @Pointcut("@annotation(cn.hdustea.aha_server.annotation.UserOperationLog)")
    public void pointcut() {
    }

    /**
     * 环绕通知，获取方法并写入Log表
     *
     * @param point 切入点
     * @return 方法返回值
     * @throws Throwable 向上抛出异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);
        return result;
    }

    /**
     * 创建日志并写入数据库
     *
     * @param joinPoint 切入点
     * @param time      方法执行耗时
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        UserOperationLog userOperationLog = new UserOperationLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        cn.hdustea.aha_server.annotation.UserOperationLog logAnnotation = method.getAnnotation(cn.hdustea.aha_server.annotation.UserOperationLog.class);
        if (logAnnotation != null) {
            // 注解上的描述
            userOperationLog.setOperation(logAnnotation.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        userOperationLog.setMethod(className + "." + methodName + "()");
        // 获取请求的参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        HashMap<String, Object> argsList = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            argsList.put(paramNames[i], args[i]);
        }
        //将参数所在的数组转换成json
        String params = null;
        try {
            params = JacksonUtil.obj2json(argsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userOperationLog.setParams(params);
        // 获取request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            // 设置IP地址
            userOperationLog.setIp(IpUtil.getIpAddr(request));
            // 从token中获取用户名
            String token = request.getHeader("Authorization");
            if (token != null) {
                String account = JWTUtil.getAccount(token);
                userOperationLog.setAccount(account);
            }
        }
        userOperationLog.setTime((int) time);
        userOperationLog.setCreateTime(new Date());
        // 保存系统日志
        userOperationLogDao.saveUserOperationLog(userOperationLog);
    }

}
