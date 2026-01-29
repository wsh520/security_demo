package com.wl.security_demo.security;

import com.wl.security_demo.controller.TestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {

        // 1. 这里可以执行额外的业务逻辑
        // 例如：获取用户信息并记录登出日志
        if (authentication != null) {
            String username = authentication.getName();
            System.out.println("用户 " + username + " 已安全登出");
        }

        // 2. 返回标准的 JSON 响应
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);

        String json = "{\"code\":200,\"msg\":\"登出成功，请重新登录\"}";
        response.getWriter().write(json);
    }
}
