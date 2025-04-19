package com.nayidemo.authentication.config;

import com.nayidemo.authentication.util.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 在SecurityConfig中  配置了addFilterBefore
 * 所以他实现的核心工作其实就是 认证、校验
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final RedisTemplate<String, String> redisTemplate;
    private final JWTUtil jwtUtil;

    // 构造器注入：Spring 会自动把容器中的 Bean 注入进来
    public JWTAuthenticationFilter(RedisTemplate<String, String> redisTemplate,
                             JWTUtil jwtUtil) {
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 请求头 拿到用户携带的token
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            // 去掉Bearer
            token = token.substring(7);

            // blacklist
            if (Boolean.TRUE.equals(redisTemplate.hasKey("blacklist_token:" + token))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("当前token失效 你可能被拉黑了");
                return;
            }

            try {
                System.out.println("正在校验身份...");
                // normal
                Claims claims = JWTUtil.parseToken(token);
                String username = claims.getSubject();
                System.out.println("当前正在登录的username: " + username);
                // package org.springframework.security.authentication; loading...
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username,null, List.of()
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("当前token无效 黑名单通过但验证失败");
                return;
            }
            filterChain.doFilter(request, response);
        }
    }
}
