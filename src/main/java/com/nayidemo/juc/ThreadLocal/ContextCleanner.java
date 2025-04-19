package com.nayidemo.juc.ThreadLocal;

import com.nayidemo.juc.ThreadLocal.context.DBConnectionContext;
import com.nayidemo.juc.ThreadLocal.context.TraceContext;
import com.nayidemo.juc.ThreadLocal.context.UserContext;

/**
 * 统一清理ThreadLocal  防止内存泄漏 -- 尤其是线程池场景 可能出现线程污染
 */
public class ContextCleanner {
    public static void clearAll(){
        UserContext.clear();
        DBConnectionContext.clear();
        TraceContext.clearTrace();
    }
}
