package com.wl.security_demo.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 处理业务异常 (你的自定义异常)
    @ExceptionHandler(BusinessException.class)
    public Map<String, Object> handleBusinessException(BusinessException e) {
        return Map.of("code", 500, "msg", e.getMessage());
    }

    // 2. 处理 Spring Security 的权限异常 (确保它们依然由 Security 逻辑或这里处理)
    // 注意：如果这里拦截了 AccessDeniedException，就不会触发 SecurityConfig 里的 Handler 了
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e; // 建议抛出，交给 Security 自己的 AccessDeniedHandler 处理
    }

    // 3. 处理其他未知异常 (防止 500 错误被 Security 掩盖)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception e) {
        e.printStackTrace(); // 打印日志方便排查
        return Map.of("code", 500, "msg", "系统内部错误：" + e.getMessage());
    }
}
