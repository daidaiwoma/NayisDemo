package com.rocketmqdemo;


import java.util.concurrent.*;

public class ThreadPoolConsumer {

    private final ThreadPoolExecutor executor;
    private String consumerGroup;

    public ThreadPoolConsumer(String consumerGroup,int coreSize, int maxSize, int queueSize) {
        this.executor = new ThreadPoolExecutor(
                coreSize,
                maxSize,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize),
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );
        this.consumerGroup = consumerGroup;
    }

    public void start() {
        System.out.println("【ThreadPoolConsumer】启动中...");

        new Thread(() -> {
            while (true) {
                try {
                    Message msg = MockBroker.poll(); // 获取消息
                    executor.submit(() -> process(msg)); // 多线程处理
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void process(Message msg) {
        String content = new String(msg.getBody());
        System.out.println("【消费线程-" + Thread.currentThread().getName() + "】处理消息：" + content);

        // TODO: 模拟业务逻辑耗时
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}
    }
}
