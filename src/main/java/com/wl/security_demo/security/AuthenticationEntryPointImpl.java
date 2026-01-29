package com.wl.security_demo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 401 未登录处理
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        System.out.println(e.getMessage());
        response.setStatus(200); // 建议返回200，由业务码code区分
        response.setContentType("application/json;charset=UTF-8");
        String json = String.format("{\"code\": %d, \"msg\": \"%s\"}", 401, "未认证或登录已过期");
        response.getWriter().write(json);
    }
}
