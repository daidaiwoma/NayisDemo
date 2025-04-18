package com.nayidemo.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/kickout/{userId}")
    public String KickOut(@PathVariable String userId) {//从路径中获取参数
        // 通过userId 拿到redis中的token
        String tokenKey = "user_token:" + userId;
        String token = redisTemplate.opsForValue().get(tokenKey);

        // 如果存在 将当前token加入黑名单 black_list 删除当前token
        // 加入黑名单意味着之后这个token也无法生效  不仅仅是下线 而是封号了
        // delete当前redis中的token信息则是让当前用户的登录状态失效 即下线 还可以登录
        // JWT是无状态的，redis想要实现jwt的下线是可以的  但是无法直接删除jwt
        // 但是可以通过black_list实现伪注销
        if (token != null) {
            // 加入黑名单 伪注销
            // 使用set集合会导致黑名单增加  内存膨胀
            // 优化：直接使用token作为key 设置TTL 自动过期 进行更加精细粒度的控制
//            redisTemplate.opsForSet().add("token_blacklist", token);
//            String blackListKey = "blacklist_token -- "+token;
//            System.out.println("blacklist key:"+blackListKey);
            // 就是这里 大错特错 redis的"1"是有特殊含义的 我天真的想做一些标识
//            redisTemplate.opsForValue().set(blackListKey, "1", 10, TimeUnit.MINUTES);
            // 如果想要封禁账号，可以直接用userId构建key token构建只封禁短期内的登录
            // 拉黑账号（限制重新登录）
            String blacklistUserKey = "blacklist_user:" + userId;
            redisTemplate.opsForValue().set(blacklistUserKey, "1", 30, TimeUnit.MINUTES);

            // 删除redis缓存中的token
            redisTemplate.delete(tokenKey);
            // 向offline_channel发送消息 触发KickOutSubscriber
            redisTemplate.convertAndSend("offline_channel",userId);
            return userId+"恭喜你 你被毕业了";
        }

        // 业务中一般会封装result
        return "用户未登录或已下线";
    }
}
