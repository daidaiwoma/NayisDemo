package com.nayidemo;

import com.nayidemo.authentication.util.JWTUtil;
import io.jsonwebtoken.Claims;

public class JwtTest {
    public static void main(String[] args) throws InterruptedException {
        String userId = "nayi";
        String token = JWTUtil.generateToken(userId,4000);
        System.out.println("token生成中 -- "+token);

        System.out.println("解析token --");
        Claims claims = JWTUtil.parseToken(token);
        System.out.println("token解析完毕"+claims.getSubject());
        if (JWTUtil.isExpired(token)) {
            System.out.println("当前token已过期");
        }else{
            System.out.println("当前token有效");
        }

        Thread.sleep(6000);
        if (JWTUtil.isExpired(token)) {
            System.out.println("当前token已过期");
        }else{
            System.out.println("当前token有效");
        }
    }
}
