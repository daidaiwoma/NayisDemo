package com.nayidemo.authentication.controller;

import com.nayidemo.authentication.annotation.LoginRequired;
import com.nayidemo.authentication.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    public String login(String username, String password) {
        // 模拟  实际业务应该查询数据库 并且写在Service
        String token;
        // 模拟登录
        if ("nayi".equals(username) && "123".equals(password)) {
            String blacklistUserKey = "blacklist_user:" + username;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(blacklistUserKey))) {
                return "你的账号已被封禁，暂时无法登录，请联系管理员";
            }

            System.out.println("login success! generate token...");
            token = JWTUtil.generateToken(username, 5000 * 60);
            // 向redis添加
            System.out.println("向redis添加权限信息...");
            redisTemplate.opsForValue().set("user_token:" + username, token,
                    30, TimeUnit.MINUTES);
            System.out.println("添加成功！token:" + token);
        } else {
            throw new RuntimeException("用户名或密码错误");
        }
        return token;
    }

    @GetMapping("/profile")
    @LoginRequired  // 自定义注解 拦截校验token
    public String getUserProfile(HttpServletRequest request) {
        // 方法返回的是Object 为了保证类型安全 强制转换一次
        String username = (String) request.getAttribute("username");
        if (username == null) {
            return "当前用户信息为空";
        }
        return "Welcome: " + username;
    }
}
