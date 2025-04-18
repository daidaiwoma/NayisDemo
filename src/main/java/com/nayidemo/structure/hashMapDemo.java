package com.nayidemo.structure;

import java.util.HashMap;
import java.util.Map;

public class hashMapDemo {
    public static void main(String[] args) {
        Map<String,Boolean> cache = new HashMap<String,Boolean>();
        Map<String,String> config = new HashMap<String,String>();
        // 用作缓存 缓存用户登录状态
        cache.put("user:1001",true);
        cache.put("user:1002",false);
        // 用作配置管理
        config.put("db.url", "jdbc:mysql://localhost:3306/db");
        config.put("redis.url", "root");
    }
}
