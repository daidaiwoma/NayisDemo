package com.nayidemo.juc.ThreadLocal.context;

import com.nayidemo.juc.ThreadLocal.User;

public class UserContext {
    private static final ThreadLocal<User> holder = new ThreadLocal<>();

    // set
    public static void setUser(User user) {
        // 调用ThreadLocal的set方法 -- set(Thread.currentThread(), value)
        holder.set(user);
    }

    // get
    public static User getUser() {
        // 通过Thread.currentThread()拿到当前线程的本地user
        return holder.get();
    }

    // clear
    public static void clear() {
        holder.remove();
    }
}
