package com.rocketmqdemo;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MockProducer producer = new MockProducer();

        ThreadPoolConsumer orderConsumer = new ThreadPoolConsumer("order-group", 3, 5, 20);
        ThreadPoolConsumer smsConsumer = new ThreadPoolConsumer("sms-group", 2, 4, 10);

        orderConsumer.start();
        smsConsumer.start();

        // 模拟发送消息
        for (int i = 0; i < 5; i++) {
            producer.send("orderTopic", ("下单消息-" + i).getBytes());
            producer.send("smsTopic", ("短信通知-" + i).getBytes());
        }
    }
}
