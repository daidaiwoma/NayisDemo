package com.nayidemo.juc;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.*;

public class ThreadPoolDemo {
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            4, // corePoolSize
            8, // maximumPoolSize
            30L, // 空闲线程存活时间
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100), // 有界队列
            new ThreadFactoryBuilder().setNameFormat("order-handler-%d").build(), // 自定义线程命名
            new ThreadPoolExecutor.AbortPolicy() // 拒绝策略：抛异常
    );

    // 模拟业务逻辑：订单处理
    public static void processOrder(String orderId) {
        // 提交任务到线程池并获取返回结果
        Future<String> future = executor.submit(() -> {
            // 模拟任务处理
            Thread.sleep(1000); // 模拟任务处理时间
            return Thread.currentThread().getName()+ "完成了订单" + orderId;
        });

        try {
            // 阻塞获取结果
            String result = future.get();
            System.out.println(Thread.currentThread().getName() + " 处理结果: " + result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // 恢复中断状态
            System.err.println(Thread.currentThread().getName() + " 任务被中断: " + orderId);
        } catch (ExecutionException e) {
            System.err.println(Thread.currentThread().getName() + " 任务执行失败: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // 模拟提交多个订单任务
        for (int i = 1; i <= 10; i++) {
            String orderId = "ORDER-" + i;
            processOrder(orderId);
        }

        // 优雅关闭线程池（业务结束后调用）
        try {
            executor.shutdown();  // 不再接受新任务
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();  // 如果 60 秒内未关闭完，强制关闭线程池
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();  // 如果当前线程被中断，则强制关闭线程池
        }
    }
}
