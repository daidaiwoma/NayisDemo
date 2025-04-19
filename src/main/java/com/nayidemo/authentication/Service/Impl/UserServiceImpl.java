package com.nayidemo.authentication.Service.Impl;

import com.nayidemo.authentication.Service.UserService;
import com.nayidemo.authentication.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private JWTUtil jwtUtil;
    @Override
    public String login(String username) {
        String token = JWTUtil.generateToken(username,5000*60);
        redisTemplate.opsForValue().set("user_token:"+username,token,30, TimeUnit.MINUTES);
        System.out.println("token:"+token);
        return token;
    }
}
