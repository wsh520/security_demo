package com.wl.security_demo.security;

import com.alibaba.fastjson2.JSON;
import com.wl.security_demo.common.Result;
import com.wl.security_demo.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)  {

        ServletUtils.renderString(response, JSON.toJSONString(Result.error(403, "对不起，您没有权限访问该资源")));
    }
}
