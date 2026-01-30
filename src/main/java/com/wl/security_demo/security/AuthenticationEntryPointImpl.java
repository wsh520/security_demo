package com.wl.security_demo.security;

import com.alibaba.fastjson2.JSON;
import com.wl.security_demo.common.AjaxResult;
import com.wl.security_demo.utils.ServletUtils;
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

        // 使用工具类返回 JSON
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(401, "认证失败，无法访问系统资源")));
    }
}
