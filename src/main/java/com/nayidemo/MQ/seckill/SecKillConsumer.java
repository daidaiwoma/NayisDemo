package com.nayidemo.MQ.seckill;

import com.nayidemo.MQ.util.RedisUtil;

public class SecKillConsumer {
    public void handleMessage(SecKillMessage message) {
        // 拼接key
        String redisKey = "seckill:msg:"+message.getMsgId();
        // try传入key和expireTime 使用setnx方法设置消息记录
        // 判断消息是否设置成功 -- 是否重复消费
        // 如果消息被处理 返回
        boolean isSuccess = RedisUtil.tryLock(redisKey, 60);
        System.out.println("isSuccess? " + isSuccess);
        // 错误记录：单元测试发现连续两次false并且都执行了逻辑
        // 原因：这里的条件应该是!isSuccess 犯了很严重的错误
        if(!isSuccess){
            System.out.println("秒杀消息处理过，无需重复处理"+message.getMsgId());
            return;
        }

        // 如果没有被处理 则继续执行处理逻辑
        System.out.println("用户："+message.getUserId()+" 商品："+message.getProductId());

        // 至此，完成了对消息的幂等控制
        // 但是后续的业务处理还没有真正开始
        //TODO 减库存 / 下订单 / 记录日志等
    }
}
