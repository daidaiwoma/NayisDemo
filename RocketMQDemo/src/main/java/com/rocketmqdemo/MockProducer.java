package com.rocketmqdemo;

public class MockProducer {
    public void send(String topic, byte[] body) {
        // 构建消息
        Message msg = new Message(topic, body);
        // 发送消息
        MockBroker.send(msg);
        // 收到ack 消息发送成功 -- 响应机制 同步 异步 单向
        System.out.println("消息已发送:"+body.length);
    }
}
