package com.nayidemo.juc.ThreadLocal.context;

import java.sql.Connection;

/**
 * 这里演示三种ThreadLocal  ThreadLocalMap
 */
public class DBConnectionContext {
    private static final ThreadLocal<Connection> holder = new ThreadLocal<>();

    //
    public static void set(Connection connection) {
        holder.set(connection);
    }

    //
    public static Connection getConnection() {
        return holder.get();
    }

    //
    public static void clear() {
        holder.remove();
    }
}
