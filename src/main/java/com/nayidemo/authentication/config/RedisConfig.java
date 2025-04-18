package com.nayidemo.authentication.config;

import com.nayidemo.authentication.subscriber.KickOutSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConfig {
    @Bean
    // 为什么使用容器 继承了LifeCycle的子类 能够进行统一的生命周期管理
    // redisMessageListenerContainer 管理后台线程池 异步监听
    // -- 后台 因为subscribe是一个阻塞操作
    public RedisMessageListenerContainer redisMsgListenerContainer(
            RedisConnectionFactory factory, KickOutSubscriber subscriber
    ) {
        // 构建redis消息监听器容器
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        // 将监听器挂载到offline_channel  一旦发布消息到这个频道 触发subscriber处理
        container.addMessageListener(subscriber,new PatternTopic("offline_channel"));
        return container;
    }
}
