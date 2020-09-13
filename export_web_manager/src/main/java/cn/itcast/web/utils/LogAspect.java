package cn.itcast.web.utils;

import cn.itcast.domain.system.SysLog;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.SysLogService;
import cn.itcast.service.system.UserService;
import cn.itcast.web.controller.BaseController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/*
  切面 = 切入点+通知

 */
@Component
@Aspect  //  @Aspect 该类是一个切面类
public class LogAspect {


    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;


    //环绕通知方法一定要有返回值
    //环绕通知也一定要有参数ProceedingJoinPoint ，ProceedingJoinPoint代表当前要执行的方法
    //切入点表达式是可以使用逻辑运算符
    @Around("execution(* cn.itcast.web.controller.*.*.*(..)) &&! execution(* cn.itcast.web.controller.system.SysLogController.*(..))")
    public Object saveLog(ProceedingJoinPoint pj) throws Throwable {
        try {
            Object result = pj.proceed();//给目标方法放行
            //目标方法执行完毕之后我们需要记录日志
            SysLog sysLog = new SysLog();
            //id
            sysLog.setId(UUID.randomUUID().toString());
            User loginUser = (User) session.getAttribute("loginUser");
            //用户名
            sysLog.setUserName(loginUser.getUserName());
            //登陆者ip
            sysLog.setIp(request.getRemoteAddr());
            //操作时间
            sysLog.setTime(new Date());
            //当前调用的方法
            String methodName = pj.getSignature().getName();//getSignature()获取当前的方法签名
            sysLog.setMethod(methodName);
            //记录方法所属的类
            String  clazzName = pj.getTarget().getClass().getName();
            sysLog.setAction(clazzName);
            //当前登陆者所属企业信息
            sysLog.setCompanyId(loginUser.getCompanyId());
            sysLog.setCompanyName(loginUser.getCompanyName());

            //保存该日志信息
            sysLogService.save(sysLog);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //aop编程捕获到的异常信息一定要抛出，如果捕获了不抛出，那么异常处理器则不知道出现了异常
            throw throwable;
        }
    }

}
