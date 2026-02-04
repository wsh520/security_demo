package com.wl.security_demo.security;

import com.alibaba.fastjson2.JSON;
import com.wl.security_demo.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    Logger LOGGER = LoggerFactory.getLogger(LogoutSuccessHandlerImpl.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {

        // 1. 这里可以执行额外的业务逻辑
        // 例如：获取用户信息并记录登出日志
        if (authentication != null) {
            String username = authentication.getName();
            LOGGER.info("用户 {} 已安全登出", username);
        }

        // 2. 返回标准的 JSON 响应
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);

        response.getWriter().write(JSON.toJSONString(Result.success("登出成功，请重新登录")));
    }
}
