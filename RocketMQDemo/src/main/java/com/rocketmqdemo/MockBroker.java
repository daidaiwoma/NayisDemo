package com.rocketmqdemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 通过MockBroker中转RocketMQ消息 模拟请求
 */
public class MockBroker {
    private static final BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();

    public static void send(Message msg) {
        boolean offer = queue.offer(msg);
        if (offer) {
            System.out.println("队列成功收到消息");
        }
        System.out.println("【Broker】收到消息：" + new String(msg.getBody()));
    }

    public static Message poll() throws InterruptedException {
        return queue.take(); // 阻塞式消费
    }
}
