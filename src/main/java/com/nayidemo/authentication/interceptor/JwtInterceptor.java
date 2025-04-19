package com.nayidemo.authentication.interceptor;

import com.nayidemo.authentication.annotation.LoginRequired;
import com.nayidemo.authentication.util.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HttpServletBean;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 因为是登录前校验 重写拦截器的前置方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 对使用了@LoginRequired的方法进行拦截 -- 需要校验jwt的
        if (handler instanceof HandlerMethod method) {
            LoginRequired loginRequired = method.getMethodAnnotation(LoginRequired.class);
            if (loginRequired != null) {
                // 有该注解 继续处理逻辑
                String token = request.getHeader("Authorization");
                if (!StringUtils.hasText(token)) {
                    throw new RuntimeException("user isn't login!");
                }

                Claims claims = JWTUtil.parseToken(token);
                String username = claims.getSubject();


                // ✅ 找出当前用户的旧 token
                String oldToken = redisTemplate.opsForValue().get("user_token:" + username);
                if (token.equals(oldToken)) {
                    String blacklistKey = "blacklist_token:" + token;
                    System.out.println("黑名单判断 key: " + blacklistKey);

                    if (Boolean.TRUE.equals(redisTemplate.hasKey(blacklistKey))) {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("Token 已失效，请重新登录");
                        return false;
                    }


                    // 修改：redis+token 黑名单  set集合 已弃用
//                    if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("token_blacklist", token))) {
//                        if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("token_blacklist", token))) {
//                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                            response.setCharacterEncoding("UTF-8");
//                            response.setContentType("application/json;charset=UTF-8");
//                            response.getWriter().write("Token失效 请重新登陆");
//                            return false;
//                        }
//                    }

                    // 修改：redis+token 黑名单
//                if (StringUtils.hasText(token)) {
//                    if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("token_blacklist", token))) {
//                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                        response.getWriter().write("Token失效 请重新登陆");
//                        return false;
//                    }
//                }

                }
                request.setAttribute("username", username);
            }
            return true;
        }
        return false;
    }
}
