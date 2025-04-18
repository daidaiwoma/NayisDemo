package com.nayidemo.MQ.seckill;

import lombok.Data;

// 构建一个秒杀的消息
@Data
public class SecKillMessage {
    // 消息唯一id
    private String msgId;
    private Long userId;
    // 商品id
    private Long productId;
}
