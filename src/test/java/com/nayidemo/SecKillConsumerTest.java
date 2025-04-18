package com.nayidemo;

import com.nayidemo.MQ.seckill.SecKillConsumer;
import com.nayidemo.MQ.seckill.SecKillMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecKillConsumerTest {
    private SecKillConsumer consumer;

    // @BeforeEach在单元测试中完成初始化操作
    @BeforeEach
    public void setUp() throws Exception {
        // 构造消费者
        consumer = new SecKillConsumer();
    }

    @Test
    public void testHandleMessage() throws Exception {
        // 构造消息
        SecKillMessage message = new SecKillMessage();
        message.setMsgId("test-001-1");
        message.setUserId(1001L);
        message.setProductId(2001L);

        // 模拟重复消费
        // first success
        consumer.handleMessage(message);
        // second fail
        consumer.handleMessage(message);
    }
}
