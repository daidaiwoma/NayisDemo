package com.nayidemo.Lock;


import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

public class RedisDistributedLock {
    //1.创建Jedis实例  实际开发建议使用连接池管理Redis网络资源
    private final Jedis jedis;

    public RedisDistributedLock(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean tryLock(String lockKey,String requestId,int expireTime){
        jedis.set(lockKey,requestId);
        return true;
    }


    public static void main(String[] args) {
        // 注意：在实际应用中建议使用连接池来获取 Jedis 实例
        // 还有，这里报错是正常的，因为没有启动Redis实例，嘻嘻
        Jedis jedis = new Jedis("localhost", 6379);
        RedisDistributedLock lock = new RedisDistributedLock(jedis);

        String lockKey = "myLock";
        String requestId = UUID.randomUUID().toString();
        int expireTime = 30000; // 30秒超时

        if (lock.tryLock(lockKey, requestId, expireTime)) {
            System.out.println("成功获取分布式锁，执行业务逻辑...");
            try {
                // 模拟业务处理
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("未能获取分布式锁，稍后重试...");
        }

        jedis.close();
    }

}
