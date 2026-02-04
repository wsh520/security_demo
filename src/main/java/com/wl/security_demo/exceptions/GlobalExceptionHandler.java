package com.wl.security_demo.exceptions;

import com.wl.security_demo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. 处理业务异常 (你的自定义异常)
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {

        LOGGER.error("业务异常: {}", e.getMessage());

        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理权限不足异常 (@PreAuthorize 抛出的)
    @ExceptionHandler(AccessDeniedException.class)
    public Result<Void> handleAccessDeniedException(AccessDeniedException e) {

        LOGGER.error("权限异常: {}", e.getMessage());

        return Result.error(403, "权限不足，请联系管理员");
    }

    // 处理认证异常
    @ExceptionHandler(AuthenticationException.class)
    public Result<Void> handleAuthenticationException(AuthenticationException e) {

        LOGGER.error("认证异常: {}", e.getMessage());

        return Result.error(401, e.getMessage());
    }

    // 3. 处理其他未知异常 (防止 500 错误被 Security 掩盖)
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {

        LOGGER.error("系统异常: {}", e.getMessage(), e);

        return Result.error(500, "系统内部错误");
    }
}
