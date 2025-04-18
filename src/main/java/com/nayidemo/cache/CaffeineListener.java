package com.nayidemo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;

public class CaffeineListener {
    public static void main(String[] args) throws InterruptedException {
        //1.定义监听器
        RemovalListener<String,String> listener = (key,value,cause)->
                System.out.println("removed key:"+key+"cause:"+cause);

        //2.创建缓存
        Cache<String,String> cache = Caffeine.newBuilder()
                .removalListener(listener)
                .build();

        cache.put("key1","value1");
        cache.put("key2","value2");

        //3.触发监听
        cache.invalidate("key1");
        cache.invalidate("key2");

        //4.为了保证看到控制台输出，强制处理--监听时间是异步的
        cache.cleanUp();

        Thread.sleep(100);
    }
}
