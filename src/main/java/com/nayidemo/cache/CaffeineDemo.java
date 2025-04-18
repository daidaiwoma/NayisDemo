package com.nayidemo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineDemo {
    public static void main(String[] args) {
        // 1.创建一个缓存实例
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(100)
                .build();

        // 2.向缓存存放数据
        // 这里有一个小细节 同一个key 后放入的生效
        cache.put("key1","value1");
        cache.put("key2","value2");

        // 3.获取缓存数据
        System.out.println(cache.getIfPresent("key1"));
        System.out.println(cache.getIfPresent("key2"));

        // 4.模拟数据过期
        try {
            Thread.sleep(TimeUnit.HOURS.toMillis(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String valueExpired = cache.getIfPresent("key1");
        System.out.println(valueExpired);
    }
}
