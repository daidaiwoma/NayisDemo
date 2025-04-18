package com.nayidemo.authentication.subscriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class KickOutSubscriber implements MessageListener {

    @Override
    //subscriber是接受并处理消息的  参数接收Message和消息来自哪个channel(byte[])
    public void onMessage(Message message, byte[] pattern) {
        String userId = new String(message.getBody());
        System.out.println("⚠ 用户 " + userId + " 被强制下线！");
    }
}
