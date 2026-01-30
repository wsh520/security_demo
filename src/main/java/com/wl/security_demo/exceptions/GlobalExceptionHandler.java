package com.wl.security_demo.exceptions;

import com.wl.security_demo.common.AjaxResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 处理业务异常 (你的自定义异常)
    @ExceptionHandler(BusinessException.class)
    public AjaxResult handleBusinessException(BusinessException e) {
        return AjaxResult.error(500, e.getMessage());
    }

    // 处理权限不足异常 (@PreAuthorize 抛出的)
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e) {
        return AjaxResult.error(403, "权限不足，请联系管理员");
    }

    // 处理认证异常
    @ExceptionHandler(AuthenticationException.class)
    public AjaxResult handleAuthenticationException(AuthenticationException e) {
        return AjaxResult.error(401, e.getMessage());
    }

    // 3. 处理其他未知异常 (防止 500 错误被 Security 掩盖)
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        e.printStackTrace(); // 打印日志方便排查

        return AjaxResult.error(500, "系统内部错误");
    }
}
