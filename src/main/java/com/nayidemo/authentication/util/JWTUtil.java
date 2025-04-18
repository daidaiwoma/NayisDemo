package com.nayidemo.authentication.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWTUtil {
    // 设定一个固定秘钥
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 定义JWT
    public static String generateToken(String userId,long expireMillis) {
        return Jwts.builder() // 接下来是jwt属性设置
                .setSubject(userId) // 设置jwt主题
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expireMillis)) // 设置过期时间
                .signWith(key) // 使用key签名
                .compact();// 执行构建并返回
    }

    // 解析JWT
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证jwt有效期
    public static boolean isExpired(String token) {
        try {
            Date expiration = parseToken(token).getExpiration();
            // before() --> return getMillisOf(this) < getMillisOf(when);
            return expiration.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

}
