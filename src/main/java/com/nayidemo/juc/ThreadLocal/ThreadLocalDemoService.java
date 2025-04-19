package com.nayidemo.juc.ThreadLocal;

import com.nayidemo.juc.ThreadLocal.context.TraceContext;
import com.nayidemo.juc.ThreadLocal.context.UserContext;
import org.springframework.stereotype.Service;

@Service
public class ThreadLocalDemoService {

    @ClearThreadLocal
    public void demo() {
        UserContext.setUser(new User("nayi","1001"));
        TraceContext.setTrace("链路追踪信息...");

        System.out.println("[Service] User: " + UserContext.getUser().getUserId());
        System.out.println("[Service] Trace: " + TraceContext.getTrace());
    }
}
