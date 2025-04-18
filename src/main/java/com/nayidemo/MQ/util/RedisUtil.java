package com.nayidemo.MQ.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

// 构建一个redis工具类
public class RedisUtil {
    private static final JedisPool jedisPool = new JedisPool("localhost", 6379);

    // setnx实现幂等 static方便直接使用util工具类
    public static boolean tryLock(String key,int expireTime){
        try(Jedis jedis = jedisPool.getResource()){
            String result = jedis.set(key, "1", SetParams.setParams().ex(expireTime).nx().ex(expireTime));
            return "OK".equals(result);
        }
    }
}
