package com.wl.security_demo.security;

import com.wl.security_demo.utils.JwtUtils;
import com.wl.security_demo.utils.RedisCacheUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;


@Component
public class LogoutHandlerImpl implements LogoutHandler {

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            // 计算 Token 剩余时间（假设工具类有这个方法）
            Claims claims = JwtUtils.parseToken(token);
            String userName = claims.getSubject();
            redisCacheUtils.deleteObject(userName);
        }
    }
}
