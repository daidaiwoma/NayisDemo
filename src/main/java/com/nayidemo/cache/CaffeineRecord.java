package com.nayidemo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineRecord {
    public static void main(String[] args) {
        //1.创建缓存
        Cache<String,String> cache = Caffeine.newBuilder()
                .recordStats()
                .build();
        //2.放入测试数据
        cache.put("key1","value1");

        //3.模拟查询
        cache.getIfPresent("key1");
        cache.getIfPresent("key1");
        cache.getIfPresent("key2");

        //3.查看缓存信息
        System.out.println(cache.stats());

    }
}
