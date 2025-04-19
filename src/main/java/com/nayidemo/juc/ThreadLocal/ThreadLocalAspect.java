package com.nayidemo.juc.ThreadLocal;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ThreadLocalAspect {
    // 定义切点
    @Pointcut("@annotation(com.nayidemo.juc.ThreadLocal.ClearThreadLocal) || @within(com.nayidemo.juc.ThreadLocal.ClearThreadLocal)")
    public void clearThreadLocal() {
    }

    @Around("clearThreadLocal()")
    public Object clearThreadLocal(ProceedingJoinPoint pjp) throws Throwable {
        try {
            System.out.println("clearThreadLocal");
            return pjp.proceed(); // 执行目标方法
        } finally {
            System.out.println("clear ThreadLocal...");
            // 调用统一封装的清理方法
            ContextCleanner.clearAll();
        }
    }
}
