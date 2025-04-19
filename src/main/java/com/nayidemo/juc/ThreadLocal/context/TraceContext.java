package com.nayidemo.juc.ThreadLocal.context;

/**
 * 由于业务和UserContext有明显区分 新建一个ThreadLocal
 */
public class TraceContext {
    private static final ThreadLocal<String> traceHolder = new ThreadLocal<>();
    //
    public static void setTrace(String trace) {
        traceHolder.set(trace);
    }

    public static String getTrace() {
        return traceHolder.get();
    }

    public static void clearTrace() {
        traceHolder.remove();
    }
}
