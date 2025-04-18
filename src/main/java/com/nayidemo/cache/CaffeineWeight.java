package com.nayidemo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.units.qual.C;

public class CaffeineWeight {
    public static void main(String[] args) {
        //1.创建一个基于大小的权重缓存
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumWeight(1000)
                .weigher((String key, String value) -> value.length())
                .build();

        cache.put("长文本", "这是一段超级超级长的文本...");
        System.out.println(cache.getIfPresent("长文本"));
    }

}
