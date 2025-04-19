package com.rocketmqdemo;

import lombok.Data;

@Data
// 模拟RocketMQ消息
public class Message {
    private String topic;
    private byte[] body;

    public Message(String topic, byte[] body) {
        this.topic = topic;
        this.body = body;
    }
}
