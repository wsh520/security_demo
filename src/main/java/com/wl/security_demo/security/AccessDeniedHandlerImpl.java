package com.wl.security_demo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setStatus(200); // 建议返回200，由业务码code区分
        response.setContentType("application/json;charset=UTF-8");
        String json = String.format("{\"code\": %d, \"msg\": \"%s\"}", 403, "权限不足，拒绝访问");
        response.getWriter().write(json);
    }
}
